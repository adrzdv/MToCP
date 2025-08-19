package com.adrzdv.mtocp.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomGson {
    public static Gson create() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(RevisionObjectTypeAdapterFactory.create())
                .registerTypeAdapterFactory(WorkerDomainTypeAdapterFactory.create())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
}
