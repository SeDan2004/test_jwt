package com.example.test.repositories

import com.example.test.modules.requests.UserRequest
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import test.db.example.Tables.USERS

@Repository
class UserRepositories {

    @Autowired
    private lateinit var dsl: DSLContext

    fun checkUser(user: UserRequest) =
            dsl.fetchExists(USERS, USERS.NAME.eq(user.name).and(USERS.PASSWORD.eq(user.password)))

    fun reg(user: UserRequest) =
            dsl.insertInto(USERS)
                    .columns(USERS.NAME, USERS.PASSWORD)
                    .values(user.name, user.password)
                    .returningResult(USERS.ID)
                    .fetchInto(Int::class.java)
                    .get(0)

    fun auth(user: UserRequest) =
            dsl.select(USERS.ID)
                    .from(USERS)
                    .where(USERS.NAME.eq(user.name).and(USERS.PASSWORD.eq(user.password)))
                    .fetchInto(Int::class.java)
                    .get(0)
}