package com.example.expanse.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.expanse.R
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.extensions.LayoutContainer
import com.example.expanse.data.Transaction
import kotlinx.android.synthetic.main.list_item.view.*
import android.view.LayoutInflater


class TransactionAdapter(private val listener: (Long) -> Unit) :
    ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransactionAdapter.ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition).id)
            }
        }

        fun bind (transaction: Transaction){
            with(transaction){
                itemView.transaction_name.text=transaction.transactionName
                itemView.transaction_date.text= transaction.date
                itemView.transaction_mode.text=transaction.type.toString()
                itemView.transaction_amount.text=transaction.amount
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}