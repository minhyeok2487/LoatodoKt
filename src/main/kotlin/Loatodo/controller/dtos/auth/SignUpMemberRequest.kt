package Loatodo.controller.dtos.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class SignUpMemberRequest (

    @field:Schema(description = "회원 이메일")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val username: String,

    @field:Schema(description = "이메일 인증번호")
    val number: Int,

    @field:Schema(description = "비밀번호")
    val password: String,

    @field:Schema(description = "비밀번호 확인")
    val equalPassword: String
)