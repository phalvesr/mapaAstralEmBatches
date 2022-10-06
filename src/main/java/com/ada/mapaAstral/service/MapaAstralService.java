package com.ada.mapaAstral.service;

import com.ada.mapaAstral.enumeration.Signo;
import com.ada.mapaAstral.model.MapaAstral;
import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.repository.PessoaRepository;
import com.ada.mapaAstral.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MapaAstralService {

    private final PessoaRepository repository = new PessoaRepository();

    public List<Pessoa> getPessoas() {
        try {
            return repository.getPessoas();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gravaInformacoesPessoa(Pessoa pessoa) {
        MapaAstral mapaAstral = mapaAstral(pessoa.getDataNascimento(), pessoa.getZoneId().toString());

        try {
            repository.createArquivoGravaPessoa(pessoa, mapaAstral);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String buscaSignoPorEnun(LocalDateTime datanascimento) {
        MonthDay monthDayNascimento = MonthDay.of(datanascimento.getMonth(), datanascimento.getDayOfMonth());

        return Arrays.stream(Signo.values())
                .filter(s -> Util.isWithinRange(monthDayNascimento, s.getDataComeco(), s.getDataFim()))
                .findFirst()
                .map(Enum::toString)
                .orElse("Não tem signo!!");
    }

    public String buscaPorSigno(LocalDate datanascimento) {
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

    public String procurarAscendente(String signo, LocalTime horarioDeNascimento) {
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

    public MapaAstral mapaAstral(LocalDateTime dataHoraNascimento, String localNascimento) {

        localizarIdade(dataHoraNascimento);
        buscaPorZona(dataHoraNascimento);
        formarDataDeNascimento(dataHoraNascimento);

        String signo = buscaPorSigno(dataHoraNascimento.toLocalDate());
        String ascendente = procurarAscendente(signo, dataHoraNascimento.toLocalTime());
        String signoLunar = localizarSingnoLunar(dataHoraNascimento.toLocalTime(), localNascimento);

        return new MapaAstral(signo, ascendente, signoLunar);
    }

    private void localizarIdade(LocalDateTime dataHoraNascimento) {
        Period idade = Period.between(dataHoraNascimento.toLocalDate(), LocalDate.now());
        System.out.println("idade: " + idade.getYears());

    }

    private void buscaPorZona(LocalDateTime dataHoraNascimento) {
        ZoneId zoneId = ZoneId.of("America/Recife");
        System.out.println(zoneId);
        ZoneOffset currentOffsetForMyZone = zoneId.getRules().getOffset(dataHoraNascimento);
        System.out.println(currentOffsetForMyZone);
    }

    private void formarDataDeNascimento(LocalDateTime dataHoraNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String format = formatter.format(dataHoraNascimento);
        System.out.println(format);
    }

    private void buscaPorAcendente(LocalDateTime dataHoraNascimento) {
        if (dataHoraNascimento.getYear() > 1976) {
            System.out.println("Ascendente: " + procurarAscendente(buscaPorSigno(dataHoraNascimento.toLocalDate()), dataHoraNascimento.toLocalTime().minusHours(2)));
        } else if (dataHoraNascimento.getYear() > 1946 && dataHoraNascimento.getYear() < 1975) {
            System.out.println("Ascendente: " + procurarAscendente(buscaPorSigno(dataHoraNascimento.toLocalDate()), dataHoraNascimento.toLocalTime().minusHours(2)));
        } else {
            System.out.println("Ascendente: " + procurarAscendente(buscaPorSigno(dataHoraNascimento.toLocalDate()), dataHoraNascimento.toLocalTime()));
        }
    }

    public String localizarSingnoLunar(LocalTime time, String localNascimento) {
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
}


/*
-- singnoLunar
- Se a pessoa nasceu Em Recife e depois das 12h00, deve  retornar "Casimiro"
- Se a pessoa nasceu Em Cuiaba e antes das 12h00, deve  retornar "Odin"
- Se a pessoa nasceu Em São Paulo (não importa o horario), deve retornar "Gandalf"
- Em qualquer outro caso, deve retornar: "Dinossauro"

 */
