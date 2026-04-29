package com.example.budgetapplication.data.repository

import com.example.budgetapplication.data.dao.UserDAO

class LoginRepository(
    private val userDAO: UserDAO

) {
    suspend fun getUser() = userDAO.getUserPasswordEmail()

    suspend fun  setEmail(): String{
        return getUser().userEmail_s
    }
}