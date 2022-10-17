package com.technipixl.eval4_and.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technipixl.eval4_and.model.ExpenseType

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre WHERE genreId = :genreId LIMIT 1")
    fun findById(genreId: Long): LiveData<ExpenseType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenseType: ExpenseType)

    @Query("SELECT * FROM ExpenseType")
    fun getAll(): LiveData<List<ExpenseType>>

    @Query("SELECT * FROM ExpenseType")
    suspend fun getAllGenre(): List<ExpenseType>
}