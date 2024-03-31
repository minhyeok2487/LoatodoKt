package Loatodo.controller.dtos

import io.swagger.v3.oas.annotations.media.Schema

data class SimpleResponse (

    @field:Schema(description = "성공이면 true")
    val success : Boolean,

    @field:Schema(description = "처리 내용")
    val message : String
)