package com.adrzdv.mtocp.data.db.converter;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @TypeConverter
    public static LocalDate fromString(String str) {
        return str == null ? null : LocalDate.parse(str, formatter);
    }

    @TypeConverter
    public static String toString(LocalDate date) {
        return date == null ? null : date.format(formatter);
    }
}
