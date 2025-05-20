package com.adrzdv.mtocp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum ErrorCodes {
    SUCCESS("000#SUCCESS", "Данные загружены"),
    UNKNOWN_ERROR("000#UNKNOWN", "Неизвестная ошибка"),
    UPDATE_ERROR("001#UPDATE", "Ошибка обновления элемента"),
    LOAD_ERROR("002#DATA_LOAD", "Ошибка загрузки данных"),
    NOT_FOUND_ERROR("003#NOT_FOUND:", "Данные не найдены"),
    INPUT_ERROR("004#INPUT", "Ошибка формата воода даннных"),
    UNHANDLED_METHOD("005#UNHANDLE", "Экспорт не поддерживается для этого типа");

    @NonNull
    private final String errorCode;
    @NonNull
    private final String errorText;

    ErrorCodes(@NonNull String errorCode, @NonNull String errorHeader) {
        this.errorCode = errorCode;
        this.errorText = errorHeader;
    }

    @NonNull
    public String getErrorTitle() {
        return this.errorText;
    }

    @NonNull
    public String getErrorCode() {
        return this.errorCode;
    }

    @Nullable
    public static ErrorCodes fromCode(String code) {
        for (ErrorCodes e : values()) {
            if (e.errorCode.equals(code)) return e;
        }
        return null;
    }

    @NonNull
    public String toString() {
        return errorCode + ":\n" + errorText;
    }
}
