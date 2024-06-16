package com.backend.challenge.domain.services

import com.backend.challenge.domain.exceptions.ClaimsInvalidException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class ValidateJwtServiceTest {

    @Test
    fun ` should receive a valid jwt token`() {
        val service = ValidateJwtService()
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg"

        val result = service.isValid(token)

        Assertions.assertThat(result).isTrue()
    }

    @Test
    fun `should receive claim name with number and return false`() {
        val service = ValidateJwtService()
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs"

        val result = service.isValid(token)

        Assertions.assertThat(result).isFalse()
    }

    @Test
    fun `must receive an exceeded number of claims and return false`() {
        val service = ValidateJwtService()
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY"

        assertThrows<ClaimsInvalidException> {
            service.isValid(token)
        }
    }

    @Test
    fun `should receive claim with invalid role and return false`() {
        val service = ValidateJwtService()
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiSW50ZXJuYWwiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.qh_zWjybZIsYqppkCYlHhxZ2gwJd223O-YD_jNb5K-k"

        val result = service.isValid(token)

        Assertions.assertThat(result).isFalse()
    }

    @Test
    fun `should receive seed without prime number and return false`() {
        val service = ValidateJwtService()
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiSW50ZXJuYWwiLCJTZWVkIjoiNjAwIiwiTmFtZSI6IlZhbGRpciBBcmFuaGEifQ.gRuO4ZZRno6BjE_5TdHqEBqgXHtGEGxm7aZrjWjcOcc"

        val result = service.isValid(token)

        Assertions.assertThat(result).isFalse()
    }

    @Test
    fun `should receive a exceeded name characters and return false`() {
        val service = ValidateJwtService()
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiSW50ZXJuYWwiLCJTZWVkI" +
                    "joiNjAwIiwiTmFtZSI6IkFsZXNzYW5kcmEgTWFyaWEgT2xpdmVpcmE" +
                    "gU2lsdmEgQ29zdGEgRmVycmVpcmEgZG9zIFNhbnRvcyBTb3V6YSBMa" +
                    "W1hIENhcnZhbGhvIE1lbmRlcyBQZXJlaXJhIEdvbWVzIEJhdGlzdGE" +
                    "gUm9kcmlndWVzIE1vbnRlaXJvIE1hcnRpbnMgQWxtZWlkYSBGb25zZ" +
                    "WNhIEFyYcO6am8gQmFycm9zIE5vZ3VlaXJhIFJpYmVpcm8gVGVpeGV" +
                    "pcmEgTW9yZWlyYSBGZXJuYW5kZXMgZGEgU2lsdmEgQW5kcmFkZSBGa" +
                    "Wd1ZWlyZWRvIENhc3RybyBQaXJlcyBkZSBDYXJ2YWxobyBlIFNpbHZ" +
                    "hIn0.fk_QZKhUEF040EMnWui3XyTmmle6A80tEji1k6LAJVI"

        val result = service.isValid(token)

        Assertions.assertThat(result).isFalse()
    }

}