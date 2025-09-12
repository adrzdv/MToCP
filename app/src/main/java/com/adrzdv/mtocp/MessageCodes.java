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
    PATTERN_MATCHES_ERROR("016#PATTERN_ERROR", "Ошибка соответствия данных"),
    DUPLICATE_ERROR("017#DUPLICATE", "Объект уже добавлен"),
    CAST_ERROR("018#CAST_ERROR", "Ошибка класса"),
    PARAMETER_ERROR("019#PARAM_ERROR", "Отсутствуют параметры для удаления"),
    DELETE_SUCCESS("020#DELETED", "Удалено успешно"),
    EMPTY_STRING("021#EMPTY_STR", "Не может быть пустым"),
    EMPTY_COUNT("022#EMPTY_REVISION", "Список объектов пуст"),
    PARAMS_NOT_DONE("023#STAT_PARAMS_ERROR", "Не все дополнительные параметры заполнены"),
    DUPLICATE_VIOLATION_ERROR("024#DUP_VIOLATION", "Нарушение уже добавлено"),
    PHOTO_SUCCESS("025#PH_SUCCESS", "Фото добавлено"),
    PHOTO_ERROR("026#PH_ERROR", "Ошибка добавления фото"),
    VIDEO_SUCCESS("025#VID_SUCCESS", "Видео добавлено"),
    VIDEO_ERROR("026#VID_ERROR", "Ошибка добавления видео"),
    PERMISSION_ERROR("027#PERMISSION_DENIED", "Отказано в доступе"),
    NFC_UNAVAILABLE("028#NFC_UNAVAILABLE", "NFC модуль отсутствует"),
    FILE_ERROR("029#FILE_ERROR", "Файл(ы) отсутствуют");

    @NonNull
    private final String errorCode;
    @NonNull
    private final String errorText;

    MessageCodes(@NonNull String errorCode, @NonNull String errorHeader) {
        this.errorCode = errorCode;
        this.errorText = errorHeader;
    }

    @NonNull
    public String getMessageTitle() {
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
