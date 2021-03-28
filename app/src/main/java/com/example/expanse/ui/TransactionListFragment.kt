package com.example.expanse.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expanse.R
import com.example.expanse.R.id.*
import kotlinx.android.synthetic.main.fragment_transaction_list.*


class TransactionListFragment : Fragment() {

    //    lateinit var sharedPreferences:SharedPreferences
    private lateinit var viewModel: TransactionListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(activity)
/* when user clicks the transaction in recycler view then it will navigate to fragment with details of the transaction */
            adapter = TransactionAdapter {
                findNavController().navigate(
                    TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetailFragment(
                        it,
                    )
                )
            }
        }

/* to update to net balace remaining after every addition in the recycler view  and always use this
 this method to update the shared preference rather then the method used in login fragment */
        val sharedPreferences: SharedPreferences =
            this.requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        setNetBalance(sharedPreferences, editor)

//        to set the name in the recycler view fragment
        name_text.text = sharedPreferences.getString("Name", "illuminati").toString()


/* for tool bar on click listener */
        addAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                profile -> {
                    findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToSavedProfileFragment())
                    true
                }

                calender_view -> {
                    findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToDayTransactionFragment())
                    true
                }
                else -> false
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { it ->
            when (it.itemId) {

                daily -> {
                    true
                }

                add_expanse -> {
                    findNavController().navigate(
                        TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetailFragment(
                            0
                        )
                    )
                    true
                }

                monthly -> {
                    findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToMonthlyTransactionsFragment())
                    true
                }
                else -> false
            }
        }

        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as TransactionAdapter).submitList(it)
        })

//        for net amount

//        val sharedPreferencesp : SharedPreferences = this.requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
//        val netAmount = sharedPreferencesp.getString("Budget","0")
//
//        amount_remaining.text=netAmount
    }


    private fun setNetBalance(
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ) {

//        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
//        println("Name ${sharedPreferences.getString("Name","0")} Budget ${sharedPreferences.getString("Budget","0")}")
        var remainingAmount: Float = sharedPreferences.getString("Budget", "0")!!.toFloat()

        println("Net Amount $remainingAmount")
        amount_remaining.text = remainingAmount.toString()
        viewModel.netAmount.observe(viewLifecycleOwner, Observer {


            if (it != null) {
                remainingAmount = sharedPreferences.getString("Budget", "0")!!.toFloat()
                remainingAmount += it

//                sharedPreferences.edit().putString("Budget", remainingAmount.toString()).apply()
//                remainingAmount.toString().also { amount_remaining.text = it }

                editor.putString("Budget", remainingAmount.toString())
                amount_remaining.text = remainingAmount.toString()
                if (remainingAmount >= 0)
                    amount_remaining.setTextColor(Color.parseColor("#ADFF2F"))
                else if (remainingAmount < 0) {
                    amount_remaining.setTextColor(Color.parseColor("#ff726f"))
                }
            }
        })

        viewModel.netAmountCash.observe(viewLifecycleOwner, Observer {
            netCash.text = it.toString()
        })

        viewModel.netAmountCredit.observe(viewLifecycleOwner, Observer {
            netCredit.text = it.toString()
        })

        viewModel.netAmountDebit.observe(viewLifecycleOwner, Observer {
            netDebit.text = it.toString()
        })

    }
}