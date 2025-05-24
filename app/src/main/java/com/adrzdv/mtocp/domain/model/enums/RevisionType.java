package com.adrzdv.mtocp.domain.model.enums;

import java.util.Arrays;
import java.util.List;

public enum RevisionType {
    ALL("Полный список"),
    IN_TRANSIT("В пути"),
    AT_START_POINT("В пункте формирования"),
    AT_TURNROUND_POINT("В пункте оборота"),
    AT_TICKET_OFFICE("В пункте продажи");

    private final String revisionTypeTitle;

    RevisionType(String revisionTypeTitle) {
        this.revisionTypeTitle = revisionTypeTitle;
    }

    public String getRevisionTypeTitle() {
        return revisionTypeTitle;
    }

    public static RevisionType fromString(String revisionTypeTitle) {
        for (RevisionType revisionType : RevisionType.values()) {
            if (revisionType.revisionTypeTitle.equalsIgnoreCase(revisionTypeTitle)) {
                return revisionType;
            }
        }
        throw new IllegalArgumentException("Неизвестный тип проверки: " + revisionTypeTitle);
    }

    public static List<String> getListOfTypes() {

        return Arrays.stream(RevisionType.values())
                .map(RevisionType::getRevisionTypeTitle)
                .toList();
    }
}
