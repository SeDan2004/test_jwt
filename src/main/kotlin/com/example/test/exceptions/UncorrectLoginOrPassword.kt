package com.example.test.exceptions

class UncorrectLoginOrPassword : Exception {
    constructor(msg: String) : super(msg)
}