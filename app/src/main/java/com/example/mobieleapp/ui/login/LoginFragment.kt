package com.example.mobieleapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mobieleapp.CameraActivity
import com.example.mobieleapp.R
import com.example.mobieleapp.SecondActivity
import com.example.mobieleapp.data.database.dorm.DormActivity
import com.example.mobieleapp.data.database.wordbrol.WordActivity
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
        val addkot = view.findViewById<Button>(R.id.btn_addkotpage)
        val word = view.findViewById<Button>(R.id.btn_word)

        logi.setOnClickListener {

            val intent = Intent(activity, loginActivity::class.java)
            startActivity(intent)
        }

        word.setOnClickListener {
            val intent = Intent(activity, WordActivity::class.java)
            startActivity(intent)
        }

        addkot.setOnClickListener {
            val intent = Intent(activity, DormActivity::class.java)
            startActivity(intent)
        }

    }
}