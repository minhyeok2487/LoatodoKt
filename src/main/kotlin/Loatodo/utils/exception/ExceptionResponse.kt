package Loatodo.utils.exception

data class ExceptionResponse(
    var errorCode: Int,

    val exceptionName: String,

    val errorMessage: String,
)
