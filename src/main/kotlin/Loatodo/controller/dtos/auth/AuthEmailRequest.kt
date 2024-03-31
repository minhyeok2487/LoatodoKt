package Loatodo.controller.dtos.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class AuthEmailRequest(

    @field:Schema(description = "이메일")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,

    @field:Schema(description = "인증번호")
    val number: Int
)