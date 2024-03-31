package Loatodo.controller.dtos.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class AuthEmailRequest(

    @field:Schema(description = "이메일")
    @field:NotNull(message = "이메일은 필수 입력값입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,

    @field:Schema(description = "인증번호")
    @field:NotNull(message = "이메일은 필수 입력값입니다.")
    val number: Int
)