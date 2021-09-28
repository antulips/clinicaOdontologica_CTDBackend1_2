package com.ClinicaOdontologica.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Date {
    public static Timestamp dateToTimestamp(java.util.Date date){
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;

    }

    public static java.sql.Date convertLocalDateToSqlDate(LocalDate fechaAConvertir) {
        return java.sql.Date.valueOf(fechaAConvertir);
    }

    public static LocalDate convertSqlDateALocalDate(java.sql.Date fecha) {
        return Instant.ofEpochMilli(fecha.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
