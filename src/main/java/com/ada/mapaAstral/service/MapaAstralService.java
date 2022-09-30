package com.ada.mapaAstral.service;

import com.ada.mapaAstral.Signo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MapaAstralService {

    public String buscaSignoPorEnun(LocalDateTime datanascimento) {
        MonthDay monthDayNascimento = MonthDay.of(datanascimento.getMonth(), datanascimento.getDayOfMonth());

        for (Signo signo : Signo.values()) {
            if (isWithinRange(monthDayNascimento, signo.getDataComeco(), signo.getDataFim())) {
                System.out.println(signo.getNome());
                return signo.toString();
            }

        }
        System.out.println("Não tem signo");
        return "Não tem signo!!";

    }

    public String buscaPorSigno(LocalDate datanascimento) {
        MonthDay monthDayNascimento = MonthDay.of(datanascimento.getMonth(), datanascimento.getDayOfMonth());

        MonthDay ariesStartDate = MonthDay.of(3, 21);
        MonthDay ariesEndDate = MonthDay.of(4, 20);

        MonthDay sagitarioStartDate = MonthDay.of(11, 22);
        MonthDay sagitarioEndDate = MonthDay.of(12, 21);

        if (isWithinRange(monthDayNascimento, ariesStartDate, ariesEndDate)) {
            return "Aries";
        } else if (isWithinRange(monthDayNascimento, sagitarioStartDate, sagitarioEndDate)) {
            return "Sagitario";
        }

        return "Não tem signo!!";

    }

    private boolean isWithinRange(MonthDay dataNascimento, MonthDay startDate, MonthDay endDate) {
        return !(dataNascimento.isBefore(startDate) || dataNascimento.isAfter(endDate));
    }

    private boolean isWithinRange(LocalTime horarioDeNascimento, LocalTime startTime, LocalTime endTime) {
        return !(horarioDeNascimento.isBefore(startTime) || horarioDeNascimento.isAfter(endTime));
    }

    public String procurarAscendente(String signo, LocalTime horarioDeNascimento) {
        if ("Aries".equalsIgnoreCase(signo)) {
            if (isWithinRange(horarioDeNascimento, LocalTime.of(18, 31), LocalTime.of(20, 30))) {
                return "escorpião";
            }
        } else if ("sagitario".equalsIgnoreCase(signo)) {
            if (isWithinRange(horarioDeNascimento, LocalTime.of(10, 31), LocalTime.of(12, 30))) {
                return "Peixes";
            }

        }

        return "Ufa, não tem ascendente";
    }

    public void mapaAstral(LocalDateTime dataHoraNascimento) {

        localizarIdade(dataHoraNascimento);
        buscaPorZona(dataHoraNascimento);
        formarDataDeNascimento(dataHoraNascimento);

        System.out.println("Ano Bissexto:  " + dataHoraNascimento.toLocalDate().isLeapYear());
        System.out.println("Signo: " + buscaPorSigno(dataHoraNascimento.toLocalDate()));


        buscaPorAcendente(dataHoraNascimento);

        System.out.println("####################");
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


//        System.out.println("Signo Lunar: " + singnoLunar(dataHoraNascimento.toLocalTime(), "Recife"));

//    public String singnoLunar(LocalTime time, String localNascimento){
//        Set<String> zones = ZoneId.getAvailableZoneIds();
//        LocalTime localTime = LocalTime.now();
//
//        System.out.println(zones);
//        System.out.println(zones);
//
//        for(String s : zones) {
//            if (s.contains(zone)) {
//                ZoneId zoneId = ZoneId.of(s);
//                System.out.println(zoneId);
//            }
//        }
//        return " ";
}



/*
-- singnoLunar
- Se a pessoa nasceu Em Recife e depois das 12h00, deve  retornar "Casimiro"
- Se a pessoa nasceu Em Cuiaba e antes das 12h00, deve  retornar "Odin"
- Se a pessoa nasceu Em São Paulo (não importa o horario), deve retornar "Gandalf"
- Em qualquer outro caso, deve retornar: "Dinossauro"

 */
