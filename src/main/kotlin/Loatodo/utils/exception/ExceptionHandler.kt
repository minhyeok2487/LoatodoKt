package Loatodo.utils.exception

import Loatodo.utils.slack.SlackService
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

@RestControllerAdvice
class ExceptionHandler(
    private val slackService: SlackService
) {
    val log = LoggerFactory.getLogger(this.javaClass)!!

    private fun handleException(exception: Exception, errorMessage: String)
            : ResponseEntity<ExceptionResponse> {
        log.warn(errorMessage)
        slackService.sendSlackMessage(errorMessage)
        return ResponseEntity(
            ExceptionResponse(
                errorCode = HttpStatus.BAD_REQUEST.value(),
                exceptionName = exception.javaClass.simpleName,
                errorMessage = errorMessage
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(RuntimeException::class, IllegalArgumentException::class)
    fun handleRuntimeExceptions(exception: RuntimeException): ResponseEntity<ExceptionResponse> {
        val errorMessage = exception.message.toString()
        return handleException(exception, errorMessage)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        val errorMessage = exception.bindingResult.fieldError?.defaultMessage.toString()
        return handleException(exception, errorMessage)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<ExceptionResponse> {
        val cause = exception.cause
        val errorMessage = if (cause is MismatchedInputException) {
            "${cause.path[0].fieldName} 필드의 값이 잘못되었습니다."
        } else {
            "확인할 수 없는 형태의 데이터가 들어왔습니다"
        }
        return handleException(exception, errorMessage)
    }
}
