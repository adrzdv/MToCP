package com.adrzdv.mtocp.domain.model.enums;

public enum WorkerTypes {
    TRAIN_MANAGER("Начальник поезда", "ЛНП"),
    DINNER_MASTER("Директор вагона-ресторана", "ДВР"),
    DINNER_MANAGER("Менеджер вагона-бистро", "МВБ"),
    CONDUCTOR("Проводник", "ППВ"),
    POSTAL_MANAGER("Начальник почтового вагона", "ВПН"),
    POSTAL_MECHANIC("Проводник-механик",""),
    SENIOR_CONDUCTOR("Старший проводник","Ст.ППВ"),
    MECHANIC("Поездной электромеханик","ПЭМ"),
    OUTSOURCE("Работник сторонней организации",""),
    CASHIER("Билетный кассир","БК"),
    SENIOR_CASHIER("Старший билетный кассир","Ст.БК");

    private final String description;
    private final String shortName;

    WorkerTypes(String description, String shortName) {
        this.description = description;
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public String getShortName(){
        return shortName;
    }

}
