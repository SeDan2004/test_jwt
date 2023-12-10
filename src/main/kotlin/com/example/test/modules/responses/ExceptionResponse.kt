package com.example.test.modules.responses

import org.springframework.http.HttpStatus
import java.time.LocalDate

data class ExceptionResponse(
        val msg: String,
        val status: HttpStatus,
        val date: LocalDate
)