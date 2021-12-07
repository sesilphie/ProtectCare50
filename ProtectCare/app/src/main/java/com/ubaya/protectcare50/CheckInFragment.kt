package com.ubaya.protectcare50

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.fragment_check_in.*
import kotlinx.android.synthetic.main.fragment_check_in.view.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [CheckInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val newPlaces = arrayListOf<Place>()
        val view = inflater.inflate(R.layout.fragment_check_in, container, false)
        val selectSpinner = Place("----", "Select a place")
        newPlaces.add(selectSpinner)
        val queue = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160819027/uas/displayPlace_protectCare50.php"
        val stringRequest = StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("checkparams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()){
                        val playObj = data.getJSONObject(i)
                        with (playObj){
                            val place = Place(
                                getString("code_place"),
                                getString("name")
                            )
                            newPlaces.add(place)
                        }
                    }
                    Log.d("listPlacecheck", GlobalData.place.toString())
                    if (newPlaces != GlobalData.place){
                        GlobalData.place = newPlaces
                    }
                    //create array adapter spinner
                    val adapter = ArrayAdapter(view.context, R.layout.myspinner_layout, GlobalData.place)
                    adapter.setDropDownViewResource(R.layout.myspinner_item_layout)
                    view.spinnerPlace.adapter = adapter
                }
            },
            Response.ErrorListener {
                Log.d("paramserror", it.message.toString())
            }
        )
        queue.add(stringRequest)

        view.buttonCheckIn.setOnClickListener {
            val inputCode = textInputCode.text.toString()
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Calendar.getInstance().time
            val currentDate = formatter.format(date)
            if (inputCode == ""){
                Toast.makeText(context, "Please input Unique Code", Toast.LENGTH_SHORT).show()
            }else{
                if (GlobalData.user.vaccine_doses != "0"){
                    val queue = Volley.newRequestQueue(activity)
                    val url = "https://ubaya.fun/native/160819027/uas/checkIn_protectCare50.php"
                    val stringRequest = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener {
                            Log.d("checkparams", it)
                            val obj = JSONObject(it)
                            if (obj.getString("result") == "CHECKIN"){
                                val data = obj.getJSONObject("data")
                                with(data){
                                    GlobalData.checkInPlace = Place(getString("code_place"), getString("name"))
                                }
                                Toast.makeText(context, "CHECK IN SUCCESS", Toast.LENGTH_SHORT).show()
                                val intent = Intent(activity, MainActivity::class.java)
                                activity?.startActivity(intent)
                                activity?.finish()

                            }else{
                                Toast.makeText(context, "CHECK IN FAILED. invalid Unique Code $inputCode", Toast.LENGTH_SHORT).show()
                            }
                        },
                        Response.ErrorListener {
                            Log.d("paramserror", it.message.toString())
                        }
                    ){
                        override fun getParams(): MutableMap<String, String> {
                            return hashMapOf("code" to inputCode, "username" to GlobalData.user.username,
                                "vaccine" to GlobalData.user.vaccine_doses,"checkIn" to currentDate)
                        }
                    }
                    queue.add(stringRequest)
                }
                else{
                    Toast.makeText(context, "Vaccine doses must be at least 1 time", Toast.LENGTH_SHORT).show()
                }
            }

        }
        return view


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CheckInFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}