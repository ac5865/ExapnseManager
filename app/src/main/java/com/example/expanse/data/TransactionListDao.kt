package com.example.expanse.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import java.util.*

@Dao
interface TransactionListDao {
    @Query("SELECT * FROM `transaction` WHERE date=:date ORDER BY id ")
    fun getTransactionById(date: String):LiveData<List<Transaction>>

    @Query("Select * from `transaction` order by id ASC")
    fun getAllTransaction():LiveData<List<Transaction>>
}