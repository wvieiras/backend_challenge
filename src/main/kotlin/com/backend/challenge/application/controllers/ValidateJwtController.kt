package com.backend.challenge.application.controllers

import com.backend.challenge.domain.services.ValidateJwtService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ValidateJwtController(
    private val service: ValidateJwtService
) {
    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/")
    fun validateJwt(@RequestParam token: String) = runCatching {
        logger.info("RequestParam with token $token")
        if (service.isValid(token)) "verdadeiro" else "falso"
    }.getOrElse {
        "falso"
    }

}