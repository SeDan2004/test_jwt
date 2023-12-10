package com.example.test.exceptions

class UserExists : Exception {
    constructor(msg: String) : super(msg)
}