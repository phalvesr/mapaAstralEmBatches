package com.ada.mapaAstral.util;

import java.time.LocalTime;
import java.time.MonthDay;

public class Util {
    public static boolean isWithinRange(MonthDay dataNascimento, MonthDay startDate, MonthDay endDate) {
        return !(dataNascimento.isBefore(startDate) || dataNascimento.isAfter(endDate));
    }

    public static boolean isWithinRange(LocalTime horarioDeNascimento, LocalTime startTime, LocalTime endTime) {
        return !(horarioDeNascimento.isBefore(startTime) || horarioDeNascimento.isAfter(endTime));
    }
}
