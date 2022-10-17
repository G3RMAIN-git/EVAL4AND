package com.technipixl.eval4_and.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.technipixl.eval4_and.db.ExpenseRepository
import com.technipixl.eval4_and.model.Expense
import com.technipixl.eval4_and.model.ExpenseWithGenre


internal class ExpenseDetailViewModel: ViewModel() {
        var liveDataBook: LiveData<ExpenseWithGenre>? = null

        fun getExpense(context: Context, expenseId: Long): LiveData<ExpenseWithGenre>? {
            //liveDataExpense = ExpenseRepository.getExpenseDetails(context, expenseId)
            val liveDataExpense = ExpenseRepository.getExpenseDetails(context, expenseId)
            return liveDataExpense
        }

        fun deleteExpense(context: Context, expenseId: Long) {
            ExpenseRepository.deleteExpense(context, expenseId)
        }
    }
