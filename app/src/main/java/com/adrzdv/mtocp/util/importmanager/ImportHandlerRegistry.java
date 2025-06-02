package com.adrzdv.mtocp.util.importmanager;

import com.adrzdv.mtocp.util.importmanager.handlers.ImportHandler;

import java.util.HashMap;
import java.util.Map;

public class ImportHandlerRegistry {
    private final Map<Class<?>, ImportHandler<?>> handlers;

    public ImportHandlerRegistry() {

        handlers = new HashMap<>();
    }

    public <T> void register(Class<T> clazz, ImportHandler<T> handler) {
        handlers.put(clazz, handler);
    }

    @SuppressWarnings("unchecked")
    public <T> ImportHandler<T> getHandler(Class<T> clazz) {
        return (ImportHandler<T>) handlers.get(clazz);
    }

}
