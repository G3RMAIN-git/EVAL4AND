package com.technipixl.eval4_and.model

import androidx.room.PrimaryKey
import java.util.*

data class Expense(
    /*
    @PrimaryKey(autoGenerate = true)
    var ExpenseId: Long = 0,

     */
    var date: Date,
    var name: String,
    var value: Float
)
