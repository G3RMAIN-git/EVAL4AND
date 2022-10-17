package com.technipixl.eval4_and.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technipixl.eval4_and.model.ExpenseGenre

@Dao
interface ExpenseGenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: ExpenseGenre)

    @Query("SELECT * FROM expensegenre")
    fun getAll(): LiveData<List<ExpenseGenre>>
}