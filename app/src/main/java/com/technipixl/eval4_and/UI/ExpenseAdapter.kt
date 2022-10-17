package com.technipixl.eval4_and.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.eval4_and.databinding.ExpenseItemBinding
import com.technipixl.eval4_and.model.Expense

class ExpenseAdapter(
    model:
    Collection<Expense>,
    private val onExpenseClicked: (Expense) -> Unit
    ) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

        private lateinit var binding: ExpenseItemBinding
        private var expenseList = mutableListOf<Expense>()

        init {
            expenseList.clear()
            expenseList.addAll(model)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
            binding = ExpenseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return ExpenseViewHolder(binding) { position ->
                if (!expenseList.isNullOrEmpty()) {
                    onExpenseClicked(expenseList[position])
                }
            }
        }

        override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
            val assetModel = expenseList[position]
            holder.bind(assetModel)
        }

        override fun onViewRecycled(holder: ExpenseViewHolder) {
            super.onViewRecycled(holder)
            holder.unbind()
        }

        override fun getItemCount(): Int {
            return expenseList.size
        }

        class ExpenseViewHolder(
            private var viewBinding: ExpenseItemBinding,
            onExpenseClicked: (Int) -> Unit
        ) : RecyclerView.ViewHolder(viewBinding.root) {

            init {
                itemView.setOnClickListener {
                    onExpenseClicked(adapterPosition)
                }
            }

            fun bind(model: Expense) {
                viewBinding.titleTextView.text = model.date.toString()
                viewBinding.authorTextView.text = model.name
                viewBinding.pagesTextView.text = model.value.toString()


                //viewBinding.pagesTextView.text = "${model.pageCount} pages"
            }

            fun unbind() {
                viewBinding.titleTextView.text = null
                viewBinding.authorTextView.text = null
                viewBinding.pagesTextView.text = null
            }
        }
    }

