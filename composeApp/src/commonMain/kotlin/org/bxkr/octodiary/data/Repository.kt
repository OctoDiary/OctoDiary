package org.bxkr.octodiary.data

import org.bxkr.octodiary.model.internal.User

interface Repository {
    fun getUser(): User
}

class RepositoryImpl : Repository {
    override fun getUser(): User {
        return User()
    }
}