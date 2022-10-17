package com.technipixl.eval4_and

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.technipixl.eval4_and.databinding.ActivityDetailBinding
import com.technipixl.eval4_and.viewmodel.ExpenseDetailViewModel


class DetailActivity : AppCompatActivity() {

        companion object {
            const val EXPANSE_ID_EXTRA = "EXPANSE_ID"
            val BOOK_ID_EXTRA = "EXPANSE_ID"
        }
        private var id: Long? = null
        private lateinit var expenseListViewModel: ExpenseDetailViewModel
        lateinit var binding: ActivityDetailBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityDetailBinding.inflate(layoutInflater)
            setContentView(binding.root)
            expenseListViewModel = ViewModelProvider(this).get(ExpenseDetailViewModel::class.java)
            id = intent.getLongExtra(BOOK_ID_EXTRA, 0)
            id?.let { expenseId ->
                expenseListViewModel.getExpense(this, expenseId)?.observe(this) { expenseWithGenreLD ->
                    expenseWithGenreLD?.let { expenseWithGenre ->
                        binding.titleTextView.text = expenseWithGenre.expense.date.toString()
                        binding.authorTextView.text = expenseWithGenre.expense.name

                        binding.pageTextView.text = expenseWithGenre.expense.value.toString()

                        //binding.pageTextView.text = "${expenseWithGenre.expense.pageCount} pages"
                        //binding.summaryTextView.text = expenseWithGenre.expense.summary
                        //binding.genreTextView.text = expenseWithGenre.expenseTypes.map { it.name }.reduce { acc, s -> "$acc, $s" }
                    }
                }
            }
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_del_expense, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id: Int = item.itemId
            if (id == R.id.deleteExpense) {
                this.id?.let {
                    expenseListViewModel.deleteExpense(this, it)
                    finish()
                }
            }
            return super.onOptionsItemSelected(item)
        }
    }
