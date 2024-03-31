package Loatodo.controller.auth

import Loatodo.controller.dtos.auth.SignUpMemberRequest
import Loatodo.entity.member.Member
import Loatodo.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth API", description = "회원가입, 로그인 관련 API")
@RestController
@RequestMapping("/auth")
class AuthController (
    private val memberService: MemberService
) {

    @Operation(summary = "1차 회원가입", description = "이메일과 비밀번호 받음")
    @PostMapping("/signup")
    fun signUpMember(@RequestBody @Valid signUpMemberRequest: SignUpMemberRequest)
    : ResponseEntity<Member> {
        return ResponseEntity(memberService.signMember(signUpMemberRequest), HttpStatus.OK)
    }
}