package org.bxkr.octodiary.domain.model.cache

enum class CacheId(val storeKey: String) {
    Profile("profile"),
    Schedule("schedule"),
    HomeworkEntries("homework_entries")
}