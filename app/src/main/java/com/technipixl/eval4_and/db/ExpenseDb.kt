package com.technipixl.eval4_and.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.technipixl.eval4_and.dao.ExpenseDao
import com.technipixl.eval4_and.dao.ExpenseGenreDao
import com.technipixl.eval4_and.dao.GenreDao
import com.technipixl.eval4_and.model.Expense
import com.technipixl.eval4_and.model.ExpenseType

import com.technipixl.eval4_and.model.ExpenseGenre

class ExpenseDb {

    @Database(entities = arrayOf(Expense::class, ExpenseGenre::class, ExpenseType::class), version = 1)
    abstract class ExpenseDatabase : RoomDatabase() {
        abstract fun expenseDao(): ExpenseDao
        abstract fun genreDao(): GenreDao
        abstract fun expenseGenreDao(): ExpenseGenreDao
        companion object {

            @Volatile
            private var sharedInstance: ExpenseDatabase? = null

            fun getDB(context: Context) : ExpenseDatabase {
                if (sharedInstance != null) return sharedInstance!!
                synchronized(this) {
                    sharedInstance = Room
                        .databaseBuilder(context, ExpenseDatabase::class.java, "expense.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    return sharedInstance!!
                }
            }
        }
    }
}