package com.technipixl.eval4_and.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technipixl.eval4_and.model.Expense
import com.technipixl.eval4_and.model.ExpenseWithGenre

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense WHERE expenseId = :expenseId LIMIT 1")
    fun findById(expenseId: Long): LiveData<Expense>

    @Query("SELECT * FROM expense WHERE expenseId = :expenseId LIMIT 1")
    fun findExpenseWithGenreById(expenseId: Long): LiveData<ExpenseWithGenre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense): Long

    @Query("SELECT * FROM expense")
    fun getAll(): LiveData<List<Expense>>

    @Query("DELETE FROM expense WHERE ExpenseId = :expenseId")
    suspend fun deleteExpense(expenseId: Long)
}