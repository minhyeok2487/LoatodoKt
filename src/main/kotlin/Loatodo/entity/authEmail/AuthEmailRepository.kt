package Loatodo.entity.authEmail

import org.springframework.data.jpa.repository.JpaRepository

interface AuthEmailRepository : JpaRepository<AuthEmail, Long> {
    fun findAllByEmail(mail: String) : List<AuthEmail>
    fun findByEmailAndNumber(email: String, number: Int): AuthEmail?
}