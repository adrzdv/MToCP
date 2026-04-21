package com.adrzdv.mtocp.domain.model.enums

enum class CoachTypes(val description: String) {
    PASSENGER_CAR("Пассажирский вагон"),
    DINNER_CAR("Вагон-ресторан"),
    COMMERCIAL_CAR("Багажные вагоны (вагоны в/из ремонта)");

    companion object {
        fun fromString(str: String): CoachTypes {
            return entries.firstOrNull { it.description == str }
                ?: throw IllegalArgumentException("Unknown coach type: $str")
        }
    }
}

