package com.example.budgetapplication.data.databasetest.seeders

import com.example.budgetapplication.data.dao.UserDAO
import com.example.budgetapplication.data.entities.User
import com.example.budgetapplication.data.databasetest.DateTimeHelper

class UserSeeder {

    suspend fun populateUsers(userDAO: UserDAO) {

        userDAO.insert(
            User(
                userName_s = "Budgeteer",
                userEmail_s = "budgeteer@wccnet.edu",
                userPassword_s = "password123",
                userDateCreated_l = DateTimeHelper.create(11, 1, 2025, 0, 0, 0).time
            )
        )

        userDAO.insert(
            User(
                userName_s = "MoneyFollower",
                userEmail_s = "moneyfollower@wccnet.edu",
                userPassword_s = "hello123",
                userDateCreated_l = DateTimeHelper.create(11, 1, 2025, 0, 0, 0).time
            )
        )

    }
}
