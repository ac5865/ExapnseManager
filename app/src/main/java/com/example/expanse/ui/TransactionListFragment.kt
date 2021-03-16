package com.example.expanse.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expanse.R
import kotlinx.android.synthetic.main.fragment_transaction_list.*


class TransactionListFragment : Fragment() {

    private lateinit var viewModel: TransactionListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile ->findNavController().navigate(TransactionListFragmentDirections.actionTransactionListFragmentToSavedProfileFragment())
        }
        return true
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
            adapter = TransactionAdapter {
                findNavController().navigate(
                    TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetailFragment(
                        it
                    )
                )
            }
        }



//        when user clicks the floating action button then navigating to next fragment
        add_transaction.setOnClickListener {
            findNavController().navigate(
                TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetailFragment(
                    0,
                )
            )
        }

//        val btn: Button= view?.findViewById(R.id.profile) ?:

//        val saved:Button= this.view!!.findViewById(R.id.profile
//        profile_btn.setOnClickListener {
//            findNavController().navigate(
//                TransactionListFragmentDirections.actionTransactionListFragmentToSavedProfileFragment()
//            )
//        }

        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as TransactionAdapter).submitList(it)
        })
    }
}