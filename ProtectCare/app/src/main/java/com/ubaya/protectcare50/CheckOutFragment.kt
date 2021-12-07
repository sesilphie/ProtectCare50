package com.ubaya.protectcare50

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.ListFragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_check_out.view.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [CheckOutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckOutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_check_out, container, false)

        val queue = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160819027/uas/getCheckOutData_protectCare50.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("checkparams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val data = obj.getJSONObject("data")
                    with(data){
                        view.textPlace.text = getString("name")
                        view.textCheckIn.text = getString("check_in")
                        if (getString("vaccine") == "1"){
                            view.cardCheckOut.setCardBackgroundColor(Color.parseColor("#fff176"))
                        } else if (getString("vaccine") == "2"){
                            view.cardCheckOut.setCardBackgroundColor(Color.parseColor("#81c784"))
                        }
                    }

                }else{
                    Toast.makeText(context, "CHECK OUT FAILED", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Log.d("paramserror", it.message.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf("username" to GlobalData.user.username)
            }
        }
        queue.add(stringRequest)

        view.buttonCheckOut.setOnClickListener {
            val queue = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/native/160819027/uas/updateHistory_protectCare50.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener {
                    Log.d("checkparams", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK"){
                        Toast.makeText(context, "CHECK OUT SUCCESS", Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, MainActivity::class.java)
                        activity?.startActivity(intent)
                        activity?.finish()

                    }else{
                        Toast.makeText(context, "CHECK OUT FAILED", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    Log.d("paramserror", it.message.toString())
                }
            ){
                override fun getParams(): MutableMap<String, String> {
                    return hashMapOf("username" to GlobalData.user.username, "code_place" to GlobalData.checkInPlace.code)
                }
            }
            queue.add(stringRequest)
        }
        return view

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CheckOutFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}