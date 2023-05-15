package com.app.unlimint.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.unlimint.R
import com.app.unlimint.data.model.JokesModel
import kotlinx.android.synthetic.main.item_layout.view.*

class UnlimintAdapter() : RecyclerView.Adapter<UnlimintAdapter.DataViewHolder>() {
    private val users = ArrayList<JokesModel>()

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: JokesModel) {
            itemView.text_joke.text = user.joke
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }


    fun updateData(list: JokesModel) {
        if (users.size == 10) {
            users.removeAt(0)
            notifyItemRemoved(0)
            users.add(list)
            notifyItemInserted(users.size - 1)
        } else {
            users.add(list)
            notifyItemInserted(users.size)
        }
    }


}