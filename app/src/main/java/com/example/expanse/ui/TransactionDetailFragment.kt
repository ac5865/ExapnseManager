package com.example.expanse.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
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


        // for calender



        val properties = mutableListOf<String>()
        TransactionType.values().forEach { properties.add(it.name) }

        val arrayAdapter =
            ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, properties)
        _type.adapter = arrayAdapter

        _type?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        income.setOnClickListener {
            saveTransactionIncome()
        }

        expense.setOnClickListener {
            saveTransactionExpanse()
        }
    }

    private fun setData(transaction: Transaction){
        TransactionName.setText(transaction.transactionName)
        etamount.setText(transaction.amount)
        dateselect.setText(transaction.date)
        fromdate.setText(transaction.fromDate)
        todate.setText(transaction.toDate)
        selectcategory.setText(transaction.category)
        comment.setText(transaction.comment)
        _type.setSelection(transaction.type)
    }

    private fun saveTransactionIncome(){
        val transactionName=TransactionName.text.toString()
        val amount=etamount.text.toString()
        val selectDate=selectcategory.text.toString()
        val fromDate=fromdate.text.toString()
        val toDate=todate.text.toString()
        val type=_type.selectedItemPosition
        val category=selectcategory.text.toString()
        val comment=comment.text.toString()

        val transaction=Transaction(viewModel.transactionId.value!!,
            transactionName,
            amount,
            selectDate,
            fromDate,
            toDate,
            category,
            type,
            comment,
            1
        )

        viewModel.saveTransaction(transaction)
        activity!!.onBackPressed()
    }

    private fun saveTransactionExpanse(){
        val transactionName=TransactionName.text.toString()
        val amount=etamount.text.toString()
        val selectDate=selectcategory.text.toString()
        val fromDate=fromdate.text.toString()
        val toDate=todate.text.toString()
        val type=_type.selectedItemPosition
        val category=selectcategory.text.toString()
        val comment=comment.text.toString()

        val transaction=Transaction(viewModel.transactionId.value!!,
            transactionName,
            amount,
            selectDate,
            fromDate,
            toDate,
            category,
            type,
            comment,
            0
        )

        viewModel.saveTransaction(transaction)
        activity!!.onBackPressed()
    }
}