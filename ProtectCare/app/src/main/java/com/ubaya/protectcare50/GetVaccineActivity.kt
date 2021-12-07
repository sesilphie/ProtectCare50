package com.ubaya.protectcare50

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_get_vaccine.*

class GetVaccineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_vaccine)

        val linearLayoutManager=LinearLayoutManager(this)
        with(vaccineView){
            layoutManager=linearLayoutManager
            setHasFixedSize(true)
            adapter=GetVaccineAdapter(GlobalData.vaccine)
        }
    }
}