package com.example.test.controllers

import com.example.test.classes.JwtProvider
import com.example.test.modules.requests.JwtRequest
import com.example.test.modules.responses.JwtResponse
import com.example.test.services.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/jwt")
class JwtController {

    @Autowired
    private lateinit var jwtService: JwtService

    @GetMapping("/access")
    fun getAccess(@RequestBody request: JwtRequest) =
            jwtService.getAccessToken(request)
}