package Loatodo.controller.auth

import Loatodo.controller.dtos.auth.AuthEmailRequest
import Loatodo.controller.dtos.auth.SendEmailRequest
import Loatodo.controller.dtos.SimpleResponse
import Loatodo.service.EmailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalStateException

@Tag(name = "Email API", description = "이메일 인증 API")
@RestController
@RequestMapping("/email")
class EmailController(
    private val emailService: EmailService
) {

    @Operation(summary = "이메일 인증번호 전송")
    @PostMapping
    fun sendEmail(@RequestBody @Valid sendEmailRequest: SendEmailRequest)
            : ResponseEntity<SimpleResponse> {
        return when {
            emailService.sendEmail(sendEmailRequest)
            -> ResponseEntity.ok(SimpleResponse(true, "인증번호 전송 성공!"))

            else -> throw IllegalStateException("인증번호 전송이 실패하였습니다.")
        }
    }

    @Operation(summary = "이메일 인증번호 인증", description = "3분이내 인증번호가 일치해야 true 리턴")
    @PostMapping("/auth")
    fun authEmail(@RequestBody @Valid authEmailRequest: AuthEmailRequest): ResponseEntity<SimpleResponse> {
        return when {
            emailService.authEmail(authEmailRequest)
            -> ResponseEntity.ok(SimpleResponse(true, "이메일 인증 성공"))

            else -> throw IllegalStateException("이메일 인증이 실패하였습니다.")
        }
    }

}