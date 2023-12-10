package com.example.test.services

import com.example.test.classes.JwtProvider
import com.example.test.exceptions.UncorrectLoginOrPassword
import com.example.test.exceptions.UserExists
import com.example.test.modules.requests.UserRequest
import com.example.test.modules.responses.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.test.repositories.UserRepositories
import test.db.example.tables.records.JUsersRecord

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepositories

    @Autowired
    private lateinit var jwtProvider: JwtProvider

    fun reg(user: UserRequest) : UserResponse {
        if (!userRepository.checkUser(user)) {
            val userId: Int = userRepository.reg(user)
            val accessToken = jwtProvider.createAccessToken(userId)
            val refreshToken = jwtProvider.createRefreshToken(userId)

            return UserResponse(accessToken, refreshToken)
        } else {
            throw UserExists("Пользователь с таким логином и паролем уже существует!")
        }
    }

    fun auth(user: UserRequest) : UserResponse {
        if (userRepository.checkUser(user)) {
            val userId: Int = userRepository.auth(user)
            val accessToken = jwtProvider.createAccessToken(userId)
            val refreshToken = jwtProvider.createRefreshToken(userId)

            return UserResponse(accessToken, refreshToken)
        } else {
            throw UncorrectLoginOrPassword("Введён неправильный логин или пароль!")
        }
    }
}