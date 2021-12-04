package com.ubaya.protectcare50

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            val username = textInputUsername.text.toString()
            val password = textInputPassword.text.toString()

            if (username == ""){
                Toast.makeText(this, "Please input Username", Toast.LENGTH_SHORT).show()
            } else if (password == ""){
                Toast.makeText(this, "Please input Password", Toast.LENGTH_SHORT).show()
            } else{
                val queue = Volley.newRequestQueue(this)
                val url = "https://ubaya.fun/native/160819027/uas/login_protectCare50.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener {
                        Log.d("checkparams", it)
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK"){
                            Toast.makeText(this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(this, "LOGIN FAILED. Username or Password is wrong", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener {
                        Log.d("paramserror", it.message.toString())
                    }
                ){
                    override fun getParams(): MutableMap<String, String> {
                        return hashMapOf("username" to username, "password" to password)
                    }
                }
                queue.add(stringRequest)
            }
        }
    }
}