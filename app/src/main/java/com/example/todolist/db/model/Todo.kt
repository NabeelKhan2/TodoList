package com.example.todolist.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val FirstName: String,
    val lastName: String,
    val age: Int
) : Parcelable
