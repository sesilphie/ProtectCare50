package com.ubaya.protectcare50

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_vaccine.view.*

class GetVaccineAdapter(val vaccines:ArrayList<GetVaccine>):RecyclerView.Adapter<GetVaccineAdapter.GetVaccinesViewHolder>() {
    class GetVaccinesViewHolder (val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetVaccinesViewHolder {
        val inflaterVaccine = LayoutInflater.from(parent.context)
        val viewVaccine = inflaterVaccine.inflate(R.layout.card_vaccine,parent,false)
        return GetVaccinesViewHolder(viewVaccine)
    }

    override fun onBindViewHolder(holder: GetVaccinesViewHolder, position: Int) {
        val vaccine =  GlobalData.vaccine[position]
        with(holder.view){
            textVaccine.text=vaccine.nameVaccine
            textVaccineAddress.text=vaccine.addressVaccine
            textVaccinePlace.text=vaccine.namePlaceVaccine

        }
    }

    override fun getItemCount()=vaccines.size

}