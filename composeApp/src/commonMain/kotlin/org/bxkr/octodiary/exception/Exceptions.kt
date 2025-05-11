package org.bxkr.octodiary.exception

class NetworkException : Exception()

abstract class ApiException(description: String? = null) : Exception(description)

open class AuthException(description: String? = null) : ApiException(description)

/** Should be thrown when token is dead according to JWT payload  */
class DeadTokenException : AuthException()