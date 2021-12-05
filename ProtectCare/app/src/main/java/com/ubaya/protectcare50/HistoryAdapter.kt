package com.ubaya.protectcare50

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_history.view.*

class HistoryAdapter(val histories: ArrayList<History>):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_history,parent,false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = histories[position]
        with(holder.view){
            txtPlace.text=history.placeName
            txtHistoryCheckIn.text=history.checkIn
            txtHistoryCheckOut.text=history.checkOut
        }
    }

    override fun getItemCount()= histories.size


}