package com.example.mobieleapp.data.database.user

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.Application
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

class UpdateProfilePicActivity : AppCompatActivity() {
    private lateinit var bitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val userViewModel: UserViewModel by viewModels {
            UserViewModelFactory((application as Application).repositoryUser)
        }
        val gson = Gson()
        val json: String? = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("user", "")
        val user: User = gson.fromJson(json, User::class.java)

        bitmap = (findViewById<ImageView>(R.id.iv_camera).drawable as BitmapDrawable).bitmap as Bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()

        updatePic(image, user, userViewModel)


    }

    private fun updatePic(pic: ByteArray, user: User, userViewModel: UserViewModel): User?{

            var updatedPic = User(user.idUser, user.username,user.password,user.role,user.email,user.phoneNr,pic)
            userViewModel.updateUser(updatedPic)
            return updatedPic
    }
}