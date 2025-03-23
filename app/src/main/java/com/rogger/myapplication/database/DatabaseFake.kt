package com.rogger.myapplication.database

import com.rogger.myapplication.molds.SettingMoldel
import com.rogger.myapplication.molds.UserAuth
import java.util.UUID.randomUUID

object DatabaseFake {
    val usersAuth = mutableListOf<UserAuth>()
    val usersString = mutableListOf<SettingMoldel>()

    var sessionAuth: UserAuth? = null
    var  sessionString: SettingMoldel? = null

    init {
        val userA = UserAuth(
            randomUUID().toString(),
            "roger",
            "roggerlvr52@gmail.com",
            "12345678",
        )

        val userB = UserAuth(
            randomUUID().toString(), "Ana Paula", "roger@gmail.com", "87654321")

        usersAuth.add(userA)
        usersAuth.add(userB)


        for (i in 0..8) {
            val user = UserAuth(
                randomUUID().toString(),
                "User$i",
                "user$i@gmail.com",
                "12345678",
                )
            usersAuth.add(user)
        }
        //       sessionAuth = usersAuth.first ()
        // followers[sessionAuth!!.uuid]?.add(usersAuth[2].uuid)
    }
}