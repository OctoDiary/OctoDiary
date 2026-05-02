package org.bxkr.octodiary.domain.model.diary

data class DiaryCapabilities(
    /** Доступна ли функция "Посещение" */
    val hasVisits: Boolean = false,
    /** Доступна ли функция "Питание"  */
    val hasMeals: Boolean = false,
    /** Можно ли создавать события */
    val canAddEvents: Boolean = false,
    /** Доступна ли функция "Подарки" */
    val hasGifts: Boolean = false,
    /** Доступна ли функция "Экзамены" */
    val hasGovExams: Boolean = false,
    /** Оправдана ли загрузка событий на несколько дней.
     * Иначе говоря, будет ли загружаться вся неделя, а не только выбранный день */
    val isMultipleDatesEventLoadingJustified: Boolean = false
)
