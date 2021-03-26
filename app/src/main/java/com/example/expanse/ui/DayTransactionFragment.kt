package com.example.expanse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expanse.R
import kotlinx.android.synthetic.main.fragment_day_transaction.*
import java.util.*


class DayTransactionFragment : Fragment() {

    private lateinit var viewModel: DayTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_transaction, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DayTransactionViewModel::class.java)

        var calendarView: CalendarView = view.findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener() { calendarView, i: Int, i1: Int, i2: Int ->
//            i1 += 1
            var day = i2
            var month = i1 + 1
            var year = i

            var date = "$day/$month/$year"
            viewModel.setDate(date)
            Toast.makeText(activity, "$date", Toast.LENGTH_SHORT).show()

            with(day_recycler_view) {
                layoutManager = LinearLayoutManager(activity)
                adapter = DayTransactionAdapter {
                }
            }

            viewModel.dayTransaction.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
                (day_recycler_view.adapter as DayTransactionAdapter).submitList(it)
            })
        })


    }
}