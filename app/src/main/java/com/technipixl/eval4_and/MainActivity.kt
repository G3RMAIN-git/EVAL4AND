package com.technipixl.eval4_and

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.eval4_and.UI.ExpenseAdapter
import com.technipixl.eval4_and.databinding.ActivityMainBinding
import com.technipixl.eval4_and.db.ExpenseRepository.Companion.expenses
import com.technipixl.eval4_and.model.Expense
import com.technipixl.eval4_and.viewmodel.ExpenseListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var bookListViewModel: ExpenseListViewModel
    private lateinit var adapter: ExpenseAdapter
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var expenseListViewModel = ViewModelProvider(this).get(ExpenseListViewModel::class.java)
        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        bookListViewModel.getExpenses(this).observeForever {
            updateDataWithExpenses(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.addExpense) {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateDataWithExpenses(expenses: List<Expense>) {
        adapter = ExpenseAdapter(expenses) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXPANSE_ID_EXTRA, it.expenseId)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


    }
}