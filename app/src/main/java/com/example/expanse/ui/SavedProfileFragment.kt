package com.example.expanse.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expanse.R
import kotlinx.android.synthetic.main.fragment_saved_profile.*

class SavedProfileFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_profile, container, false)

//        val name:String=getString("key1","name");
        sharedPreferences=this.requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)

        name.setText(sharedPreferences.getString("Name","Shivam"))
        budget.setText(sharedPreferences.getString("Budget","1000"))
        income.setText(sharedPreferences.getString("Income","1000"))
        
    }
}