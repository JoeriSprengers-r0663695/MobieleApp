package com.example.mobieleapp.data.database.dorm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobieleapp.R

class DormListAdapter : ListAdapter<Dorm, DormListAdapter.DormViewHolder>(DormListAdapter.DormComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DormListAdapter.DormViewHolder {
        return DormListAdapter.DormViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DormListAdapter.DormViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id)
    }

    class DormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dormItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: Int?) {
            dormItemView.text = text.toString()
        }

        companion object {
            fun create(parent: ViewGroup): DormListAdapter.DormViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return DormListAdapter.DormViewHolder(view)
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