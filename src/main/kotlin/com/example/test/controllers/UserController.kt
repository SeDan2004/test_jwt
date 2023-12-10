package com.example.test.controllers

import com.example.test.modules.requests.UserRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.example.test.services.UserService

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/reg")
    fun userRegistry(@RequestBody user: UserRequest) =
            ResponseEntity(userService.reg(user), HttpStatus.OK)

    @PostMapping("/auth")
    fun userAuthorized(@RequestBody user: UserRequest) =
            ResponseEntity(userService.auth(user), HttpStatus.OK)
}