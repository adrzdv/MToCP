package com.adrzdv.mtocp.ui.validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ValidatorTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "Иванов Иван Иванович",
            "Иванов И.И.",
            "Иванов И. И.",
            "Мамин-Сибиряк Д.Н.",
            "Фёдоров Артём Игоревич",
            "Салтыков-Щедрин М. Е."
        ]
    )
    fun `valid FIO should match`(fio: String) {
        assertTrue(Validator.validateWorkerName(fio), "Должно быть true для: $fio")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "вваыва",
            "аааа аа",
            "Аааа а а",
            "иванов И.И.",
            "Иванов и.И.",
            "Иванов И",
            "Иванов И. И. И.",
            "Иванов-иван И.И.",
            "Иванов  И.И."
        ]
    )
    fun `invalid FIO should not match`(fio: String) {
        assertFalse(Validator.validateWorkerName(fio), "Должно быть false для: $fio")
    }

}