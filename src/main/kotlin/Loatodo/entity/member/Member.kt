package Loatodo.entity.member

import Loatodo.controller.auth.dto.SignUpMemberRequestDto
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Member(

    // 회원 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0,

    @Column(length = 1000)
    val apiKey: String? = null,

    @Column(unique = true)
    val username: String,

    val authProvider: String? = "none",

    val accessKey: String? = null,

    val password: String,

    @CreatedDate
    @Column(updatable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    val lastModifiedDate: LocalDateTime = LocalDateTime.now()

) {
    constructor(signUpMemberRequestDto: SignUpMemberRequestDto) : this(
        username = signUpMemberRequestDto.username,
        password = BCryptPasswordEncoder().encode(signUpMemberRequestDto.password),
        apiKey = null,
        authProvider = "none",
        accessKey = null,
    )
}