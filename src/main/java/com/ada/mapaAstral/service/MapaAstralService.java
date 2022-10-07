package com.ada.mapaAstral.service;

import com.ada.mapaAstral.model.MapaAstral;
import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.repository.MapaAstralRepository;
import com.ada.mapaAstral.type.ArquivoSalvo;
import com.ada.mapaAstral.type.either.Either;
import com.ada.mapaAstral.util.Util;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MapaAstralService {

    private final MapaAstralRepository repository;
    private final PessoaService pessoaService;

    public void importarMapasAstrais() {

        List<Pessoa> pessoas = pessoaService.buscaPessoas();

        List<MapaAstral> mapasAstrais = criarMapasAstrais(pessoas);

        mapasAstrais.forEach(this::persistirMapaAstral);
    }

    private List<MapaAstral> criarMapasAstrais(List<Pessoa> pessoas) {

        return pessoas.stream()
                .map(this::montarMapaAstral)
                .toList();
    }

    private MapaAstral montarMapaAstral(Pessoa pessoa) {

        var signo = getSigno(pessoa.getDataNascimento().toLocalDate());
        var ascendente = getAscendente(signo, pessoa.getDataNascimento().toLocalTime());
        var signoLunar = getSignoLunar(pessoa.getDataNascimento().toLocalTime(), pessoa.getZoneId().getId());

        return new MapaAstral(signo, ascendente, signoLunar, pessoa);
    }

    private String getSigno(LocalDate datanascimento) {
        MonthDay monthDayNascimento = MonthDay.of(datanascimento.getMonth(), datanascimento.getDayOfMonth());

        MonthDay ariesStartDate = MonthDay.of(3, 21);
        MonthDay ariesEndDate = MonthDay.of(4, 20);

        MonthDay sagitarioStartDate = MonthDay.of(11, 22);
        MonthDay sagitarioEndDate = MonthDay.of(12, 21);

        if (Util.isWithinRange(monthDayNascimento, ariesStartDate, ariesEndDate)) {
            return "Aries";
        } else if (Util.isWithinRange(monthDayNascimento, sagitarioStartDate, sagitarioEndDate)) {
            return "Sagitario";
        }

        return "Não tem signo!!";
    }

    private String getAscendente(String signo, LocalTime horarioDeNascimento) {

        if ("Aries".equalsIgnoreCase(signo)) {
            if (Util.isWithinRange(horarioDeNascimento, LocalTime.of(18, 31), LocalTime.of(20, 30))) {
                return "escorpião";
            }
        } else if ("sagitario".equalsIgnoreCase(signo)) {
            if (Util.isWithinRange(horarioDeNascimento, LocalTime.of(10, 31), LocalTime.of(12, 30))) {
                return "Peixes";
            }
        }
        return "Ufa, não tem ascendente";
    }

    private String getSignoLunar(LocalTime time, String localNascimento) {
        Set<String> zones = ZoneId.getAvailableZoneIds();
        for (String s : zones) {
            if (s.contains((localNascimento))) {
                ZoneId zoneID = ZoneId.of(s);
                System.out.println(zoneID);

                if (zoneID.toString().equals("America/Recife") && time.isAfter(LocalTime.NOON)) {
                    return "Casimiro";
                }

                if (zoneID.toString().equals("America/Cuiaba") && time.isAfter(LocalTime.NOON)) {
                    return "Odin";
                }
                if (zoneID.toString().equals("America/Sao_Paulo")) {
                    return "Gandalf";
                }
            }
        }
        return "Dinossauro";
    }

    private void persistirMapaAstral(MapaAstral mapaAstral) {
        Either<Exception, ArquivoSalvo> resultadoPersistencia = repository.salvar(mapaAstral);

        if (resultadoPersistencia.isLeft()) {
            System.out.format("Erro ao salvar arquivo csv. Mensagem de exception: %s%n", resultadoPersistencia.unsafeGetLeft().getMessage());
        } else {
            System.out.println(resultadoPersistencia.unsafeGetRight().getMessage());
        }
    }
}
