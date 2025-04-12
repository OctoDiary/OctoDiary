package org.bxkr.octodiary.exception

class NetworkException : Exception()

abstract class ApiException(description: String? = null) : Exception(description)

open class AuthException(description: String? = null) : ApiException(description)

class IssueCallException(description: String? = null) : AuthException(description)