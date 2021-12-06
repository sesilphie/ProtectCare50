package com.ubaya.protectcare50

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_history.view.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val queue = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160819027/uas/displayHistory_protectCare50.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("checkparams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()){
                        val playObj = data.getJSONObject(i)
                        with (playObj){
                            val history = History(
                                getString("code_place"),
                                getString("check_in"),
                                getString("check_out"),
                                getString("vaccine")
                            )
                            GlobalData.history.add(history)
                        }
                    }
                    Log.d("playlistcheck", GlobalData.history.toString())
                    updateList()
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
        return view
    }

    private fun updateList() {
        val linearLayoutManager = LinearLayoutManager(activity)
        view?.historyView?.let {
            it.layoutManager = linearLayoutManager
            it.setHasFixedSize(true)
            it.adapter = HistoryAdapter(GlobalData.history)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}