package com.example.expanse.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.expanse.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val name=view.findViewById<EditText>(R.id.PersonName).toString()
        val budget=view.findViewById<EditText>(R.id.PersonBudget).toString()
        val income=view.findViewById<EditText>(R.id.PersonIncome).toString()

        sharedPreferences=this.requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)

        var islogin=sharedPreferences.getBoolean("isLogin", false)

        if(islogin==true){
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToTransactionListFragment()
            )
        }
//        profile.setOnClickListener {
//            if(islogin==true){
////                val ldf = SavedProfileFragment()
////                val args = Bundle()
////                args.putString("Key1", sharedPreferences.getString("Name",name))
////                args.putString("Key2", sharedPreferences.getString("Budget",budget))
////                args.putString("key3", sharedPreferences.getString("Income",income))
////                ldf.setArguments(args)
//////                getFragmentManager().beginTransaction().add(R.id.container, ldf).commit()
//            }
//        }

        continueButton.setOnClickListener {
            if(name!=null && budget!=null && income!=null){
                saveCredentials(name, budget, income)
            }

            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToTransactionListFragment()
            )
        }

    }

    private fun saveCredentials(name: String, budget: String, income: String) {
        sharedPreferences.edit().putBoolean("isLogin", true).apply()
        sharedPreferences.edit().putString("Name", name).apply()
        sharedPreferences.edit().putString("Budget", budget).apply()
        sharedPreferences.edit().putString("Income", income).apply()
    }
}