package com.adrzdv.mtocp.util.dataiomanager;

import com.adrzdv.mtocp.data.importmodel.ViolationImport;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class JsonIOManagerFactory<T> extends DataIOManagerFactory<T> {

    private final Map<Class<?>, Supplier<? extends DataIOManager<?>>> registry = new HashMap<>();

    public JsonIOManagerFactory() {
        registry.put(ViolationImport.class, JsonIOManagerViolation::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataIOManager<T> createManager(Class<T> entityClass) {
        Supplier<? extends DataIOManager<?>> supplier = registry.get(entityClass);
        if (supplier == null) {
            throw new IllegalArgumentException("Unsupported entity class: " + entityClass);
        }
        return (DataIOManager<T>) supplier.get();
    }
}
