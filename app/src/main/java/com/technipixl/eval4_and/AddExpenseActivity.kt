package com.technipixl.eval4_and

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.technipixl.eval4_and.databinding.ActivityAddExpenseBinding
import com.technipixl.eval4_and.db.ExpenseRepository
import com.technipixl.eval4_and.ext.hideKeyboard
import com.technipixl.eval4_and.model.ExpenseType


class AddExpenseActivity : AppCompatActivity() {

        private lateinit var genres: List<ExpenseType>
        private var selectedGenres: ArrayList<ExpenseType> = arrayListOf()
        lateinit var binding: ActivityAddExpenseBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityAddExpenseBinding.inflate(layoutInflater)
            setContentView(binding.root)
            ExpenseRepository.getAllGenres(this).observe(this) {
                this.genres = it
            }

            binding.buttonAddGenre.setOnClickListener {
                hideKeyboard(it)
                val array = genres.map { it.name }.toTypedArray()
                val selectedArray = genres.map { selectedGenres.contains(it) }.toBooleanArray()
                val builder = AlertDialog.Builder(this)
                val tmpGenres: ArrayList<ExpenseType> = arrayListOf()
                tmpGenres.addAll(selectedGenres)
                builder.setTitle("Choose a Genre.")
                builder.setMultiChoiceItems(array, selectedArray) { dialog, which, isChecked ->
                    if(isChecked) {
                        tmpGenres.add(genres[which])
                    } else {
                        tmpGenres.remove(genres[which])
                    }
                }
                builder.setPositiveButton("Ok") { dialog, which ->
                    selectedGenres = tmpGenres
                    if (selectedGenres.isEmpty()) {
                        binding.genreList.text = null
                    } else {
                        binding.genreList.text =
                            selectedGenres.map { it.name }.reduce { acc, s -> "$acc, $s" }
                    }
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_add_expense, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            hideKeyboard(binding.summaryText)
            val id: Int = item.itemId
            if (id == R.id.saveExpense) {
                val date = binding.titleText.text.toString()
                val name = binding.authorText.text.toString()
                val value = binding.pagesText.text.toString().toFloat()
                val summary = binding.summaryText.text.toString()
                if(title.isNotBlank() && name.isNotBlank() && summary.isNotBlank() && value != null && selectedGenres.isNotEmpty()) {
                    ExpenseRepository.insertBook(this, date, name, value, selectedGenres)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
                }
            }
            return super.onOptionsItemSelected(item)
        }
    }
