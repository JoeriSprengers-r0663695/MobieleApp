package com.example.mobieleapp.data.database.dorm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.R

class DormListAdapter : ListAdapter<Dorm, DormListAdapter.DormViewHolder>(DormComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormViewHolder {
        Log.d("test1",DormViewHolder.create(parent).toString())
        return DormViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DormViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.streetname,current.rent,current.housenr)
    }

    class DormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dormItemView1: TextView = itemView.findViewById(R.id.streetnameId)
        private val dormItemView2: TextView = itemView.findViewById(R.id.rentId)
        private val dormItemView3: TextView = itemView.findViewById(R.id.housnrId)

        fun bind(streetname: String?, rent: Double?, housenr : Int?) {
            dormItemView1.text = streetname.toString()
            dormItemView2.text = rent.toString()
            dormItemView3.text = housenr.toString()
        }

        companion object {
            fun create(parent: ViewGroup): DormViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.dorm_item, parent, false)
                return DormViewHolder(view)
            }
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