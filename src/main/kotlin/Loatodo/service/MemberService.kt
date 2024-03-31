package Loatodo.service

import Loatodo.controller.dtos.auth.SignUpMemberRequest
import Loatodo.entity.authEmail.AuthEmailRepository
import Loatodo.entity.member.Member
import Loatodo.entity.member.MemberRepository
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val authEmailRepository: AuthEmailRepository
) {
    // 1차 회원가입
    fun signMember(signUpMemberRequest: SignUpMemberRequest): Member {
        validateSignUpMemberRequest(signUpMemberRequest)
        return memberRepository.save(Member(signUpMemberRequest))
    }

    private fun validateSignUpMemberRequest(signUpMemberRequest: SignUpMemberRequest) {
        if(signUpMemberRequest.password != signUpMemberRequest.equalPassword) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }
        if(memberRepository.existsByUsername(signUpMemberRequest.username)) {
            throw IllegalArgumentException("이미 가입된 회원입니다. username : ${signUpMemberRequest.username} ")
        }
        if(!authEmailRepository.findAllByEmail(signUpMemberRequest.username)
            .any { email -> email.number == signUpMemberRequest.number && email.isAuth}) {
            throw IllegalStateException("이메일 인증이 실패하였습니다.")
        }
    }
}