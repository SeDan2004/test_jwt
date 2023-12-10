package com.example.test.services

import com.example.test.classes.JwtProvider
import com.example.test.modules.requests.JwtRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JwtService {

    @Autowired
    lateinit var jwtProvider: JwtProvider

    fun getAccessToken(request: JwtRequest) {
        if (jwtProvider.validateRefreshToken(request.refreshToken)) {
            println("ok!")
        }
    }
}