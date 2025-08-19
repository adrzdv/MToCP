package com.adrzdv.mtocp.util.importmanager.handlers;

import java.util.function.Consumer;

public abstract class BaseImportHandler<T> implements ImportHandler<T> {

    protected final Consumer<String> toastCallback;
    public BaseImportHandler(Consumer<String> toastCallback) {
        this.toastCallback = toastCallback;
    }
    protected void showSuccessToast(String message) {
        toastCallback.accept(message);
    }
}
