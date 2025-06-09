package com.adrzdv.mtocp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum MessageCodes {
    SUCCESS("000#SUCCESS", "Данные загружены"),
    UNKNOWN_ERROR("000#UNKNOWN", "Неизвестная ошибка"),
    UPDATE_ERROR("001#UPDATE", "Ошибка обновления элемента"),
    LOAD_ERROR("002#DATA_LOAD", "Ошибка загрузки данных"),
    NOT_FOUND_ERROR("003#NOT_FOUND:", "Данные не найдены"),
    INPUT_ERROR("004#INPUT", "Ошибка формата ввода даннных"),
    UNHANDLED_METHOD("005#UNHANDLE", "Экспорт не поддерживается для этого типа"),
    UNSUPPORTED_CLASS("006#ILLEGAL_CLASS", "Неизвестный класс"),
    DATE_ERROR("007#DATE_ERROR", "Ошибка ввода даты"),
    COACH_ERROR("008#COACH_ERROR", "Ошибка ввода номера вагона"),
    ORDER_CREATE_ERROR("009#ORDER_CREATE", "Ошибка создания уведомления"),
    BLANK_FIELDS_ERROR("010#BLANK_ERROR", "Имеются пустые поля"),
    CREATE_SUCCESS("011#SUCCESS_CREATION", "Данные успешно добавлены"),
    DIRECTORY_SUCCESS("012#DIRECTORY_SUCCESS", "Директории очищены"),
    DIRECTORY_FAIL("013#DIRECTORY_ERROR", "Ошибка очистки директорий"),
    FILE_MANAGER_ERROR("014#FMANAGER_ERROR", "Файловый менеджер отсутствует"),
    CREW_ERROR("015#CREW_ERROR", "Отсутствует ЛНП/ПЭМ"),
    PATTERN_MATCHES_ERROR("016#PATTERN_ERROR", "Ошибка соответствия данных");

    @NonNull
    private final String errorCode;
    @NonNull
    private final String errorText;

    MessageCodes(@NonNull String errorCode, @NonNull String errorHeader) {
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
    public static MessageCodes fromCode(String code) {
        for (MessageCodes e : values()) {
            if (e.errorCode.equals(code)) return e;
        }
        return null;
    }

    @NonNull
    public String toString() {
        return errorCode + ":\n" + errorText;
    }
}
