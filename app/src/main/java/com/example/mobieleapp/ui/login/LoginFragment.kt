package com.example.mobieleapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.mobieleapp.R
import com.example.mobieleapp.SecondActivity
import com.example.mobieleapp.data.database.Application
import com.example.mobieleapp.data.database.WordActivity
import com.example.mobieleapp.data.database.user.*
import com.example.mobieleapp.loginActivity


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logi = view.findViewById<Button>(R.id.login)
        val fabButton = view.findViewById<Button>(R.id.regi)
        val registreerButton = view.findViewById<Button>(R.id.signupId)



        fabButton.setOnClickListener {

            val intent = Intent(activity, WordActivity::class.java)
            startActivity(intent)
        }

        val button = view?.findViewById<Button>(R.id.button)
        button?.setOnClickListener {
            val intent = Intent(activity, SecondActivity::class.java)
            startActivity(intent)
        }

        registreerButton.setOnClickListener {

            val intent = Intent(activity, UserActivity::class.java)
            startActivity(intent)
        }


        logi.setOnClickListener {

            val intent = Intent(activity, loginActivity::class.java)
            startActivity(intent)
        }

        var test = view.findViewById<Button>(R.id.testMap)
        test.setOnClickListener {findNavController().navigate(R.id.action_loginFragment_to_testMap2)
        }
    }
}