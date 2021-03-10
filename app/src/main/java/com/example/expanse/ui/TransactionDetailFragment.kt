package com.example.expanse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.expanse.R
import com.example.expanse.data.Transaction
import com.example.expanse.data.TransactionType
import kotlinx.android.synthetic.main.fragment_transaction_detail.*

class TransactionDetailFragment : Fragment() {

    private lateinit var viewModel: TransactionDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TransactionDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val properties = mutableListOf<String>()
        TransactionType.values().forEach { properties.add(it.name) }

        val arrayAdapter =
            ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, properties)
        transaction_type.adapter = arrayAdapter

        transaction_type?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?){
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
            }
        }

        val id=TransactionDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setTransactionId(id)

        viewModel.transaction.observe(viewLifecycleOwner, Observer {
            it?.let { setData(it) }
        })

        button_income.setOnClickListener {
            saveTransaction()
        }

        expense_button.setOnClickListener {
            saveTransaction()
        }
    }

    private fun setData(transaction: Transaction){
        etTransactionName.setText(transaction.transactionName)
        etamount.setText(transaction.amount)
        etselectdate.setText(transaction.date)
        etfromdate.setText(transaction.fromDate)
        ettodate.setText(transaction.toDate)
        etselectcategory.setText(transaction.category)
        etcomment.setText(transaction.comment)
        transaction_type.setSelection(transaction.type)
    }

    private fun saveTransaction(){
        val transactionName=etTransactionName.text.toString()
        val amount=etamount.text.toString()
        val selectDate=etselectcategory.text.toString()
        val fromDate=etfromdate.text.toString()
        val toDate=ettodate.text.toString()
        val type=transaction_type.selectedItemPosition
        val category=etselectcategory.text.toString()
        val comment=etcomment.text.toString()

        val transaction=Transaction(viewModel.transactionId.value!!,
            transactionName,
            amount,
            selectDate,
            fromDate,
            toDate,
            category,
            type,
            comment
        )

        viewModel.saveTransaction(transaction)
        activity!!.onBackPressed()
    }
}