package Loatodo.entity.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByUsername(username : String) : Boolean
}