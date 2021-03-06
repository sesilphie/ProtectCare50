package com.ubaya.protectcare50

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_check_in.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/native/160819027/uas/getStatusCheckUser_protectCare50.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("checkparams", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK"){
                    val data = obj.getInt("data")
                    if(data ==1){

                        fragments.add(CheckInFragment())
                        fragments.add(HistoryFragment())
                        fragments.add(ProfileFragment())

                        viewPager.adapter = MyAdapter(this, fragments)
                    }
                    else if(data ==0){
                        fragments.add(CheckOutFragment())
                        fragments.add(HistoryFragment())
                        fragments.add(ProfileFragment())

                        viewPager.adapter = MyAdapter(this, fragments)
                    }
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



        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                bottomNav.selectedItemId = bottomNav.menu[position].itemId
            }
        })

        bottomNav.setOnItemSelectedListener {
            viewPager.currentItem = when (it.itemId){
                R.id.itemCheckIn -> 0
                R.id.itemHistory -> 1
                R.id.itemProfile -> 2
                else -> 0
            }
            true
        }
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quit ProtectCare50")
        builder.setMessage("Are you sure you want to leave this application?")
        builder.setPositiveButton("Quit", DialogInterface.OnClickListener { dialogInterface, i ->
            finish()
        })
        builder.setNegativeButton("Cancel",null)
        builder.create().show()
    }
}