package com.backend.challenge.domain.services

import com.auth0.jwt.JWT
import com.backend.challenge.domain.exceptions.ClaimsInvalidException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.math.sqrt

@Service
class ValidateJwtService {

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun isValid(token: String): Boolean = runCatching {
        logger.info("Received token jwt")
        val claims = JWT.decode(token).claims
        if (claims.size != 3) throw ClaimsInvalidException("Claims invalid Exception")

        val name = claims["Name"]?.asString()
        val role = claims["Role"]?.asString()
        val seed = claims["Seed"]?.asString()?.toIntOrNull()

        val validations = mapOf(
            "name" to nameIsValid(name),
            "role" to roleIsValid(role),
            "seed" to isPrime(seed)
        )

        return !validations.containsValue(false)

    }.getOrElse {
        logger.error("Error validating jwt $token", it)
        throw it
    }

}

private fun nameIsValid(name: String?) = when {
    name.isNullOrBlank() -> false
    (name.filter { it.isDigit() }).isNotEmpty() -> false
    (name.length > 256) -> false
    else -> true
}

private fun roleIsValid(role: String?) = role in listOf("Admin", "Member", "External")

private fun isPrime(num: Int?): Boolean {
    if (num == null || num < 2) return false
    val sqrt = sqrt(num.toDouble()).toInt()
    return (2..sqrt).none { num % it == 0 }
}