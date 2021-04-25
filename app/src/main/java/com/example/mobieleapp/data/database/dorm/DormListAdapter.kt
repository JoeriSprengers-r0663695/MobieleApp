package com.example.mobieleapp.data.database.dorm

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.DetailsPageActivity
import com.example.mobieleapp.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.Serializable

class DormListAdapter() : ListAdapter<Dorm, DormListAdapter.DormViewHolder>(DormComparator()),Serializable {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormViewHolder {
        return DormViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DormViewHolder, position: Int) {
        val current = getItem(position)

            holder.bind(current.adTitle,current.streetname,current.housenr,current.city,current.postalcode,current.rent,current.description,current.User)
    }

    class DormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener,Serializable {
        private val imagev : ImageView = itemView.findViewById(R.id.image_view)
        private val dormItemViewTitle: TextView = itemView.findViewById(R.id.adTitleId)
        private val dormItemViewRent: TextView = itemView.findViewById(R.id.rentId)
        private val dormItemViewAdress: TextView = itemView.findViewById(R.id.adresId)

       lateinit var  kot : Dorm

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(
            adTitle: String,
            streetname: String?,
            housenr: Long?,
            city: String?,
            postalcode: Long?,
            rent: String?,
            description: String?,
            owner: String?,
        ) {




            val imageref = Firebase.storage.reference.child("dorm/$adTitle/pic0")

            imageref.downloadUrl.addOnSuccessListener {Uri->

                val imageURL = Uri.toString()
              /*  Glide.with(itemView).load(imageURL).into(imagev)*/
                Picasso.get().load(imageURL).into(imagev);


            }





            dormItemViewTitle.text = adTitle
            dormItemViewRent.text = "€"+ rent
            dormItemViewAdress.text = streetname + " "+ housenr.toString() + ", " + city

            kot = Dorm(adTitle,streetname,housenr,city,postalcode,rent,description,owner)
        }

        companion object :Serializable {
            fun create(parent: ViewGroup): DormViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.dorm_item, parent, false)
                return DormViewHolder(view)
            }
        }

        override fun onClick(v: View?) {
            Log.d("kot",kot.city.toString())

            val intent = Intent(v?.context, DetailsPageActivity::class.java).putExtra("kot",
                kot)
            v?.context?.startActivity(intent)
            }
        }

    class DormComparator : DiffUtil.ItemCallback<Dorm>() {
        override fun areItemsTheSame(oldItem: Dorm, newItem: Dorm): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dorm, newItem: Dorm): Boolean {
            return oldItem.adTitle == newItem.adTitle
        }
    }
}