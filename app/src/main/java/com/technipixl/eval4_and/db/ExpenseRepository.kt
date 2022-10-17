package com.technipixl.eval4_and.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.technipixl.eval4_and.model.Expense
import com.technipixl.eval4_and.model.ExpenseGenre
import com.technipixl.eval4_and.model.ExpenseWithGenre
import com.technipixl.eval4_and.model.ExpenseType
import com.technipixl.eval4_and.dao.GenreDao

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ExpenseRepository {
    companion object {
        var expenseDatabase: ExpenseDb? = null
        var expense: LiveData<ExpenseWithGenre>? = null
        var expenses: LiveData<List<Expense>>? = null

        fun initializeDB(context: Context) : ExpenseDb.ExpenseDatabase {
            val db = ExpenseDb.ExpenseDatabase.getDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                val genres = expenseDatabase!!.genreDao().getAllGenre()
                if(genres.isNullOrEmpty()) {
                    expenseDatabase!!.genreDao().insert(ExpenseType(name = "Bouffe"))
                    expenseDatabase!!.genreDao().insert(ExpenseType(name = "Boisons"))
                    expenseDatabase!!.genreDao().insert(ExpenseType(name = "Drogues"))

                }
            }
            return db
        }

        fun insertBook(
            context: Context,
            date: String,
            name: String,
            value: Float,

            selectedExpenseTypes: ArrayList<ExpenseType>
        ) {
            expenseDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                val expense = Expense(date = date, name = name, value = value)
                val expenseId = expenseDatabase!!.expenseDao().insert(expense)
                selectedExpenseTypes.forEach {
                    val genreId = it.genreId
                    expenseDatabase!!.expenseGenreDao().insert(ExpenseGenre(expenseId = expenseId, genreId = genreId))
                }
            }
        }

        fun insertGenre(context: Context, name: String) {
            if(expenseDatabase == null) {
                expenseDatabase = initializeDB(context)
            }
            CoroutineScope(Dispatchers.IO).launch {
                val expenseType = ExpenseType(name = name)
                expenseDatabase!!.genreDao().insert(expenseType)
            }
        }

        fun getExpenseDetails(context: Context, id: Long) : LiveData<ExpenseWithGenre>? {
            if(expenseDatabase == null) {
                expenseDatabase = initializeDB(context)
            }
            expense = expenseDatabase!!.expenseDao().findBookWithGenreById(id)
            return expense
        }

        fun getAllExpenses(context: Context): LiveData<List<Expense>> {
            if(expenseDatabase == null) {
                expenseDatabase = initializeDB(context)
            }
            return expenseDatabase!!.expenseDao().getAll()
        }

        fun getAllGenres(context: Context): LiveData<List<ExpenseType>> {
            if(expenseDatabase == null) {
                expenseDatabase = initializeDB(context)
            }
            return expenseDatabase!!.genreDao().getAll()
        }

        fun deleteExpense(context: Context, bookId: Long) {
            if(expenseDatabase == null) {
                expenseDatabase = initializeDB(context)
            }
            expenseDatabase?.let { db ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.expenseDao().deleteBook(bookId)
                }
            }
        }
    }
}
