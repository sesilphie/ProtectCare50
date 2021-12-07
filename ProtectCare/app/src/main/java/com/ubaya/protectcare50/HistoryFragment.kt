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



/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayHistory()
    }

    override fun onResume() {
        super.onResume()
        displayHistory()
    }

    private fun displayHistory() {
        val newHistory =ArrayList<History>()
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
                                getString("namePlace"),
                                getString("check_in"),
                                getString("check_out"),
                                getString("vaccine")
                            )
                            newHistory.add(history)
                        }
                    }
                    Log.d("historyCheck", GlobalData.history.toString())
                    if (newHistory != GlobalData.history){
                        GlobalData.history = newHistory
                    }
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
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
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}