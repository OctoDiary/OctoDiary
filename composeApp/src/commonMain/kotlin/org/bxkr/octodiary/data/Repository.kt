package org.bxkr.octodiary.data

import org.bxkr.octodiary.model.internal.User
import org.koin.core.component.KoinComponent

interface Repository {
    fun getUser(): User
}

class RepositoryImpl : Repository, KoinComponent {

    override fun getUser(): User {
        return User()
    }
}