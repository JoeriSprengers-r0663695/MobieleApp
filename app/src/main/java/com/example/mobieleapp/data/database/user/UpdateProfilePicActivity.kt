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
    private val gson = Gson()
    private val json: String? = PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("user", "")
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Application).repositoryUser)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        bitmap = (findViewById<ImageView>(R.id.image_view).getDrawable() as BitmapDrawable).getBitmap() as Bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()

        updatePic(image)


    }

    private fun updatePic(pic: ByteArray): User?{
            val user: User = gson.fromJson(json, User::class.java)
            var updatedPic = User(user.idUser, user.username,user.password,user.role,user.email,user.phoneNr,pic)
            userViewModel.updateUser(updatedPic)
            return updatedPic
    }
}