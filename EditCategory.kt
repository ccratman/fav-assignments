package com.example.budgetapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime. *

@Entity(tableName = "USER")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "U_USER_ID")
    val userId_i: Int? = null,

    @ColumnInfo(name = "U_USER_NAME")
    val userName_s: String? = null,

    @ColumnInfo(name = "U_USER_EMAIL")
    val userEmail_s: String? = null,

    @ColumnInfo(name = "U_USER_PASSWORD")
    val userPassword_s: String? = null,

    @ColumnInfo(name = "U_DATE_CREATED")
    val userDateCreated_l: Long? = null

)
