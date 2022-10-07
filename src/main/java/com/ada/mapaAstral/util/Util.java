package com.ada.mapaAstral.util;

import com.ada.mapaAstral.model.Pessoa;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.Scanner;

public class Util {
    public static boolean isWithinRange(MonthDay dataNascimento, MonthDay startDate, MonthDay endDate) {
        return !(dataNascimento.isBefore(startDate) || dataNascimento.isAfter(endDate));
    }

    public static boolean isWithinRange(LocalTime horarioDeNascimento, LocalTime startTime, LocalTime endTime) {
        return !(horarioDeNascimento.isBefore(startTime) || horarioDeNascimento.isAfter(endTime));
    }

    public static Pessoa createPessoaFromLine(String line) {

        try (Scanner scanner = new Scanner(line)) {
            scanner.useDelimiter(",");

            String nome = scanner.next().trim();
            ZoneId zoneId = ZoneId.of(scanner.next());
            LocalDateTime dataNascimento = LocalDateTime.parse(scanner.next());

            return new Pessoa(nome, dataNascimento, zoneId);
        }
    }
}
