package com.technipixl.eval4_and.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.technipixl.eval4_and.db.ExpenseRepository
import com.technipixl.eval4_and.model.Expense

class ExpenseListViewModel: ViewModel() {
    var liveDataExpenseList: LiveData<List<Expense>>? = null

    fun getExpenses(context: Context): LiveData<List<Expense>> {
        return ExpenseRepository.getAllExpenses(context)
    }
}