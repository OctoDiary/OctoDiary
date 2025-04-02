package org.bxkr.octodiary.exception

abstract class ApiException(description: String? = null) : Exception(description)

open class AuthException(description: String? = null) : ApiException(description)

class IssueCallException(description: String? = null) : AuthException(description)