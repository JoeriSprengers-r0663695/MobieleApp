package com.example.mobieleapp.data.database.dorm

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.DetailsPageActivity
import com.example.mobieleapp.R
import com.example.mobieleapp.data.database.wordbrol.WordActivity
import com.example.mobieleapp.detailsPage
import com.example.mobieleapp.mapsActivity
import java.io.Serializable

class DormListAdapter : ListAdapter<Dorm, DormListAdapter.DormViewHolder>(DormComparator()),Serializable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormViewHolder {
        Log.d("test1",DormViewHolder.create(parent).toString())
        return DormViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DormViewHolder, position: Int) {
        val current = getItem(position)

            holder.bind(current.id,current.streetname,current.housenr,current.city,current.postalcode,current.rent,current.description)
    }

    class DormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener,Serializable {

        private val dormItemView1: TextView = itemView.findViewById(R.id.streetnameId)
        private val dormItemView2: TextView = itemView.findViewById(R.id.rentId)
        private val dormItemView3: TextView = itemView.findViewById(R.id.housnrId)

       lateinit var  kot : Dorm


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(current:Int?,streetname: String?, housenr : Int?,city :String?,postalcode:Int?,rent: Double?,description:String?) {
            dormItemView1.text = streetname.toString()
            dormItemView2.text = rent.toString()
            dormItemView3.text = housenr.toString()
            kot = Dorm(current,streetname,housenr,city,postalcode,rent,description)

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
            return oldItem.id == newItem.id
        }
    }
}