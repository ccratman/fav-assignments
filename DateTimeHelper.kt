package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT U_USER_NAME, U_USER_PASSWORD, U_USER_EMAIL, U_DATE_CREATED FROM USER ORDER BY U_DATE_CREATED DESC LIMIT 1")
    suspend fun getUserPasswordEmail(): UserPasswordEmail
}
data class UserPasswordEmail(
    @ColumnInfo(name = "U_USER_NAME")
    val userName_s: String,

    @ColumnInfo(name = "U_USER_PASSWORD")
    val userPassword_s: String,

    @ColumnInfo(name = "U_USER_EMAIL")
    val userEmail_s: String,

    @ColumnInfo(name = "U_DATE_CREATED")
    val userDateCreated_l: Long
)