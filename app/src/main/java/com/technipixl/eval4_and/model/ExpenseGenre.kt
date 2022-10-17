package com.technipixl.eval4_and.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation


    @Entity(primaryKeys = ["expenseId", "genreId"])
    data class ExpenseGenre(
        var expenseId: Long,
        var genreId: Long
    )

    data class ExpenseWithGenre(
        @Embedded
        val expense: Expense,
        @Relation(
            parentColumn = "expenseId",
            entityColumn = "genreId",
            associateBy = Junction(ExpenseGenre::class)
        )
        val expenseTypes: List<ExpenseType>
    )

    data class GenreWithExpense(
        @Embedded
        val expenseType: ExpenseType,
        @Relation(
            parentColumn = "genreId",
            entityColumn = "expenseId",
            associateBy = Junction(ExpenseGenre::class)
        )
        val expenses: List<Expense>
    )
