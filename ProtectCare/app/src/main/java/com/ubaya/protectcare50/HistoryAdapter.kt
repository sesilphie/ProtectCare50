package com.ubaya.protectcare50

import android.graphics.Color
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
            for (i in 0 until GlobalData.place.size){
                if (history.placeCode == GlobalData.place[i].code){
                    textPlaceCard.text = GlobalData.place[i].placeName
                }
            }
            for (i in 0 until GlobalData.history.size){
                if (history.vaccineDoses=="1"){
                    cardListHistory.setCardBackgroundColor(Color.parseColor("#fff176"))
                } else if (history.vaccineDoses=="2"){
                    cardListHistory.setCardBackgroundColor(Color.parseColor("#81c784"))
                }
            }
            textHistoryCheckIn.text=history.checkIn
            textHistoryCheckOut.text=history.checkOut
        }
    }

    override fun getItemCount()= histories.size


}