package Loatodo.service

import Loatodo.controller.auth.dto.SignUpMemberRequestDto
import Loatodo.entity.member.Member
import Loatodo.entity.member.MemberRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class MemberService (
    private val memberRepository: MemberRepository
) {
    // 1차 회원가입
    fun signMember(signUpMemberRequestDto: SignUpMemberRequestDto): Member {
        if(memberRepository.existsByUsername(signUpMemberRequestDto.username))
            throw IllegalArgumentException("이미 가입된 회원입니다. username : ${signUpMemberRequestDto.username} ")

        return memberRepository.save(Member(signUpMemberRequestDto))
    }
}