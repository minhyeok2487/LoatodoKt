package Loatodo.exception

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    val log = LoggerFactory.getLogger(this.javaClass)!!

    @ExceptionHandler(RuntimeException::class)
    fun exceptionHandler(exception: RuntimeException)
            :ResponseEntity<ExceptionResponse> {
        log.warn("${exception.message}")
        return ResponseEntity(
            ExceptionResponse(
                errorCode = HttpStatus.BAD_REQUEST.value(),
                exceptionName = exception.javaClass.simpleName,
                errorMessage = exception.message.toString()
            ),
            HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun exceptionHandler(exception: IllegalArgumentException)
    :ResponseEntity<ExceptionResponse> {
        log.warn("${exception.message}")
        return ResponseEntity(
            ExceptionResponse(
                errorCode = HttpStatus.BAD_REQUEST.value(),
                exceptionName = exception.javaClass.simpleName,
                errorMessage = exception.message.toString()
            ),
            HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun exceptionHandler(exception: MethodArgumentNotValidException)
            :ResponseEntity<ExceptionResponse> {
        log.warn(exception.message)
        return ResponseEntity(
            ExceptionResponse(
                errorCode = HttpStatus.BAD_REQUEST.value(),
                exceptionName = exception.javaClass.simpleName,
                errorMessage = exception.bindingResult.fieldError?.defaultMessage.toString()
            ),
            HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun exceptionHandler(exception: HttpMessageNotReadableException)
    : ResponseEntity<ExceptionResponse> {
        val cause = exception.cause
        if (cause is MismatchedInputException) {
            return ResponseEntity.badRequest()
                .body(ExceptionResponse(
                    errorCode = HttpStatus.BAD_REQUEST.value(),
                    exceptionName = MismatchedInputException::class.simpleName.toString(),
                    errorMessage = "${cause.path[0].fieldName} 필드의 값이 잘못되었습니다."
                ))
        }
        return ResponseEntity.badRequest()
            .body(ExceptionResponse(
                errorCode = HttpStatus.BAD_REQUEST.value(),
                exceptionName = exception.javaClass.simpleName,
                errorMessage = "확인할 수 없는 형태의 데이터가 들어왔습니다"))
    }


}