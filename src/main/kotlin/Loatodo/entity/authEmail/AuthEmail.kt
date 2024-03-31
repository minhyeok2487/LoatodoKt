package Loatodo.entity.authEmail

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class AuthEmail (

    // 이메일 인증
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_email_id")
    val id: Long = 0,

    @Column(length = 1000)
    val email: String,

    @Column
    val number: Int,

    @Column
    var isAuth: Boolean = false,

    @CreatedDate
    @Column(updatable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    val lastModifiedDate: LocalDateTime = LocalDateTime.now()
) {
    constructor(email: String, number: Int) : this(
        email = email,
        number = number,
        isAuth = false
    )

}