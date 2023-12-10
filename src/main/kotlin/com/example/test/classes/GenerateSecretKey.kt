package com.example.test.classes

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import javax.crypto.SecretKey

class GenerateSecretKey {
    fun generateKey() =
            Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS256).encoded)
}

fun main() {
    val generator: GenerateSecretKey = GenerateSecretKey()

    generator.generateKey()
    generator.generateKey()
}