package com.example.expanse.data

import android.app.Application
import androidx.lifecycle.LiveData

class TransactionListRepository(context: Application) {
    private val transactionListDao: TransactionListDao =
        TransactionDatabase.getDatabase(context).transactionListDao()

    fun getTransactions():LiveData<List<Transaction>>{
        return transactionListDao.getAllTransaction()
    }
}
