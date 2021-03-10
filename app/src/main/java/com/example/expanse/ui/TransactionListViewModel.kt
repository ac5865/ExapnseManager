package com.example.expanse.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.expanse.data.TransactionListRepository
import com.example.expanse.data.Transaction

class TransactionListViewModel (application: Application):AndroidViewModel(application){
    private val repo:TransactionListRepository= TransactionListRepository(application)

    val transactions:LiveData<List<Transaction>>
        get() =repo.getTransactions()
}
