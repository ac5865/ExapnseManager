package com.example.expanse.ui

import android.content.res.ColorStateList
import android.graphics.Color
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
import kotlinx.android.synthetic.main.list_item.*


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
                if(transaction.type==1){
                    itemView.transaction_mode.text="Cash"
                }

                else if(transaction.type==2){
                    itemView.transaction_mode.text="Debit Card"
                }

                else{
                    itemView.transaction_mode.text="Credit Card"
                }

                itemView.transaction_amount.text=transaction.amount

                itemView.transaction_date.text=transaction.date

//                itemView.plus_minus.text= transaction.plusMinus.toString()

                if(transaction.plusMinus==1){
                    itemView.plus_minus.text="+"
                    itemView.plus_minus.setTextColor(Color.GREEN)
                    itemView.transaction_amount.setTextColor(Color.GREEN)
                    itemView.type_view.setBackgroundColor(Color.GREEN)
                }

                else if(transaction.plusMinus==0){
                    itemView.plus_minus.text="-"
                    itemView.plus_minus.setTextColor(Color.RED)
                    itemView.transaction_amount.setTextColor(Color.RED)
                    itemView.type_view.setBackgroundColor((Color.RED))
                }
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