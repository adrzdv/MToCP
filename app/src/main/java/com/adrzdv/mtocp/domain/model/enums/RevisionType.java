package com.adrzdv.mtocp.domain.model.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum of revision types
 */
public enum RevisionType {
    ALL("Полный список", 0),
    IN_TRANSIT("В пути", 1),
    AT_START_POINT("В пункте формирования", 1),
    AT_TURNROUND_POINT("В пункте оборота", 1),
    AT_TICKET_OFFICE("В пункте продажи", 2);

    private final String revisionTypeTitle;
    private final int objType;

    RevisionType(String revisionTypeTitle, int objType) {
        this.revisionTypeTitle = revisionTypeTitle;
        this.objType = objType;
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

    public static List<String> getMovableTypes() {
        return Arrays.stream(RevisionType.values())
                .filter(revisionType -> revisionType.objType == 1)
                .map(RevisionType::getRevisionTypeTitle)
                .toList();
    }
}
