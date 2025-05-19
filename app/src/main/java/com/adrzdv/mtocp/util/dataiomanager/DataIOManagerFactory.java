package com.adrzdv.mtocp.util.dataiomanager;

public abstract class DataIOManagerFactory<T> {
    public abstract DataIOManager<T> createManager(Class<T> entityClass);

}


