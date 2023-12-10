package com.example.test.modules.responses

data class JwtResponse(
        val accessToken: String?,
        val refreshToken: String?
)