package com.example.expanse.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum  class CategoryLevel{
    other
}

enum class TransactionType{
    Cash,Debit_Card,Credit_Card
}


@Entity(tableName = "transaction")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val transactionName:String,
    val amount:String,

    @ColumnInfo(name = "date")
    val date:String,
    val fromDate: String,
    val toDate:String,

    @ColumnInfo(name="detail")
    val category: String,
    val type:Int,
    val comment: String

)