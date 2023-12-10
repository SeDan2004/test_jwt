package com.example.test.classes

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey
import kotlin.io.encoding.Base64

@Component
class JwtProvider {
    private val accessKey: SecretKey
    private val refreshKey: SecretKey

    constructor(@Value("\${jwt.secret.access}") access: String,
                @Value("\${jwt.secret.refresh}") refresh: String) {
        accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(access))
        refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refresh))
    }

    fun createAccessToken(userId: Int) : String {
        val now: LocalDateTime = LocalDateTime.now()
        val accessTokenInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant()
        val accessExpiration = Date.from(accessTokenInstant)

        return Jwts.builder()
                .setSubject("Access token")
                .setExpiration(accessExpiration)
                .signWith(accessKey)
                .claim("user_id", userId)
                .compact()
    }

    fun createRefreshToken(userId: Int) : String {
        val now: LocalDateTime = LocalDateTime.now()
        val refreshTokenInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant()
        val refreshExpiration = Date.from(refreshTokenInstant)

        return Jwts.builder()
                .setSubject("Refresh token")
                .setExpiration(refreshExpiration)
                .claim("user_id", userId)
                .signWith(refreshKey)
                .compact()
    }

    fun validateAccessToken(accessToken: String) : Boolean {
        return validateToken(accessKey, accessToken)
    }

    fun validateRefreshToken(refreshToken: String) : Boolean {
        return validateToken(refreshKey, refreshToken)
    }

    fun validateToken(token: SecretKey, tokenStr: String) : Boolean {
        try {
            Jwts.parser().build().parseClaimsJws(tokenStr)
            return true
        } catch (ex: Exception) {
            return false
        }
    }

    fun getAccessClaims(tokenStr: String) =
        getClaims(accessKey, tokenStr)


    fun getRefreshClaims(tokenStr: String) =
        getClaims(refreshKey, tokenStr)


    private fun getClaims(token: SecretKey, tokenStr: String) : Claims {
        return Jwts.parser().build().parseClaimsJws(tokenStr).body
    }
}