package Loatodo.service

import Loatodo.controller.dtos.auth.AuthEmailRequest
import Loatodo.controller.dtos.auth.SendEmailRequest
import Loatodo.entity.authEmail.AuthEmail
import Loatodo.entity.authEmail.AuthEmailRepository
import Loatodo.entity.member.MemberRepository
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.webjars.NotFoundException
import java.time.Duration
import java.time.LocalDateTime

@Service
class EmailService (
    private val javaMailSender: JavaMailSender,
    private val authEmailRepository: AuthEmailRepository,
    private val memberRepository: MemberRepository
) {
    private var number = 0
    val log = LoggerFactory.getLogger(this.javaClass)!!

    @Value("\${spring.mail.username}")
    private val senderEmail: String? = null

    @Transactional
    fun sendEmail(sendEmailRequest: SendEmailRequest): Boolean {
        validateSignUpMemberRequest(sendEmailRequest)
        val message = createMail(sendEmailRequest.email)
        try {
            javaMailSender.send(message)
        } catch (exception : MailException) {
            log.warn("MailException : ${exception.message}")
        }
        authEmailRepository.save(AuthEmail(sendEmailRequest.email, number))
        log.info("인증번호 전송 성공 $number")
        return true
    }

    @Transactional(readOnly = true)
    fun validateSignUpMemberRequest(sendEmailRequest: SendEmailRequest) {
        if(memberRepository.existsByUsername(sendEmailRequest.email)) {
            throw IllegalArgumentException("이미 가입된 회원입니다. username : ${sendEmailRequest.email} ")
        }
    }

    @Transactional
    fun authEmail(authEmailRequest: AuthEmailRequest): Boolean {
        val authEmail = validateAuthEmailRequest(authEmailRequest)
        authEmail.isAuth = true
        return true
    }

    @Transactional(readOnly = true)
    fun validateAuthEmailRequest(authEmailRequest: AuthEmailRequest): AuthEmail {
        val authMail = authEmailRepository.findByEmailAndNumber(authEmailRequest.email, authEmailRequest.number)
            ?: throw NotFoundException("인증 이메일을 찾을 수 없습니다.")

        val timeElapsedMinutes = Duration.between(authMail.createdDate, LocalDateTime.now()).toMinutes()
        if (timeElapsedMinutes > 3) throw IllegalArgumentException("만료된 인증번호 입니다.")

        return authMail
    }


    private fun createMail(mail: String?): MimeMessage {
        createNumber()
        val message: MimeMessage = javaMailSender.createMimeMessage()
        try {
            message.setFrom(senderEmail)
            message.setRecipients(MimeMessage.RecipientType.TO, mail)
            message.setSubject("이메일 인증")
            var body = ""
            body += "<h1>LoaTodo에 가입해주셔서 감사합니다.</h3>"
            body += "<h3>" + "인증 번호입니다." + "</h3>"
            body += "<p>" + "3분내로 입력해주세요." + "</p>"
            body += "<h4> 인증번호 : $number</h4>"
            message.setText(body, "UTF-8", "html")
        } catch (e: MessagingException) {
            println(e.toString())
        }
        return message
    }

    private fun createNumber() {
        number = (Math.random() * 90000).toInt() + 100000
    }
}