package com.ubaya.protectcare50

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_get_vaccine.*
import org.json.JSONObject

class GetVaccineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_vaccine)

        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160819027/uas/displayGetVaccine_protectCare50.php"
        val stringRequest = StringRequest(
            Request.Method.POST,
            url,
            Response.Listener {
                Log.d("checkParams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()){
                        val playObj = data.getJSONObject(i)
                        with (playObj){
                            val vaccines = GetVaccine(
                                getString("nameplace_vaccine"),
                                getString("name_vaccine"),
                                getString("address")
                            )
                            GlobalData.vaccine.add(vaccines)
                        }
                    }
                    Log.d("playlistcheck", GlobalData.vaccine.toString())
                    updateListVaccine()
                }
            },
            Response.ErrorListener {
                Log.e("apierror", it.message.toString())
            }
        )
        queue.add(stringRequest)

    }

    private fun updateListVaccine() {
        val linearLayoutManager=LinearLayoutManager(this)
        with(vaccineView){
            layoutManager=linearLayoutManager
            setHasFixedSize(true)
            adapter=GetVaccineAdapter(GlobalData.vaccine)
        }
    }
}