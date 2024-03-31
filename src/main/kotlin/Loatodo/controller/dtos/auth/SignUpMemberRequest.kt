package Loatodo.controller.dtos.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class SignUpMemberRequest (

    @field:Schema(description = "회원 이메일")
    @field:NotNull(message = "회원 이메일은 필수 입력값입니다.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val username: String,

    @field:Schema(description = "이메일 인증번호")
    @field:NotNull(message = "인증 번호는 필수 입력값입니다.")
    val number: Int,

    @field:Schema(description = "비밀번호")
    @field:NotNull(message = "비밀번호는 필수 입력값입니다.")
    val password: String,

    @field:Schema(description = "비밀번호 확인")
    @field:NotNull(message = "비밀번호 확인은 필수 입력값입니다.")
    val equalPassword: String
)