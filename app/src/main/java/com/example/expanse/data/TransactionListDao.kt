package com.example.expanse.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import java.util.*

@Dao
interface TransactionListDao {
    @Query("SELECT * FROM `transaction` WHERE date=:date")
    fun getTransactionByDate(date: String):LiveData<List<Transaction>>

    @Query("Select * from `transaction` WHERE  date >= date('now') order by id ASC")
    fun getAllTransaction():LiveData<List<Transaction>>

    @Query("SELECT Sum(amount) FROM `transaction`")
        fun getAmount(): LiveData<Float>
}