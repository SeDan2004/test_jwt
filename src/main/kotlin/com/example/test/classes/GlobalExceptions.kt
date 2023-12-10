package com.example.test.classes

import com.example.test.exceptions.UncorrectLoginOrPassword
import com.example.test.exceptions.UserExists
import com.example.test.modules.responses.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDate

@ControllerAdvice
class GlobalExceptions {


    @ExceptionHandler(UserExists::class)
    fun userExists(ex: Exception) : ResponseEntity<ExceptionResponse> {
        val msg = ex.message!!
        val status = HttpStatus.CREATED
        val date = LocalDate.now()

        val response = ExceptionResponse(msg, status, date)

        return ResponseEntity<ExceptionResponse>(response, status)
    }

    @ExceptionHandler(UncorrectLoginOrPassword::class)
    fun uncorrectLoginOrPassword(ex: Exception) : ResponseEntity<ExceptionResponse> {
        val msg = ex.message!!
        val status = HttpStatus.UNAUTHORIZED
        val date = LocalDate.now()

        val response = ExceptionResponse(msg, status, date)

        return ResponseEntity<ExceptionResponse>(response, status)
    }
}