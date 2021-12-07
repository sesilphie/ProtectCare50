package com.ubaya.protectcare50

import android.content.DialogInterface
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonRegister.setOnClickListener {
            val username = textInputUsername.text.toString()
            val fullName = textInputFullName.text.toString()
            val password = textInputPassword.text.toString()
            val confirmPassword = textInputConfirmPassword.text.toString()
            var vaccine_doses = 0
            if (radioButton0.isChecked){
                vaccine_doses = 0
            } else if (radioButton1.isChecked){
                vaccine_doses = 1
            } else if (radioButton2.isChecked){
                vaccine_doses = 2
            }
            if (username == ""){
                Toast.makeText(this, "Please Input Username", Toast.LENGTH_SHORT).show()
            } else if (fullName == ""){
                Toast.makeText(this, "Please Input Full Name", Toast.LENGTH_SHORT).show()
            } else if (password == ""){
                Toast.makeText(this, "Please Input Password", Toast.LENGTH_SHORT).show()
            } else if (confirmPassword == ""){
                Toast.makeText(this, "Please Input Confirm Password", Toast.LENGTH_SHORT).show()
            } else{
                if (password == confirmPassword){
                    val queue = Volley.newRequestQueue(this)
                    val url = "https://ubaya.fun/native/160819027/uas/register_protectCare50.php"
                    val stringRequest = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener {
                            Log.d("checkregister", it)
                            val obj = JSONObject(it)
                            if (obj.getString("result") == "OK"){
                                val builder = android.app.AlertDialog.Builder(this)
                                with (builder)
                                {
                                    setTitle("Register")
                                    setMessage("Register User Successfully")
                                    setPositiveButton("OK"){dialogInterface, i ->

                                        finish()
                                    }
                                    setCancelable(false)
                                    create().show()
                                }

                            }else if (obj.getString("result") == "ERROR"){
                                Toast.makeText(this, "Register User Failed. " + obj.getString("message"), Toast.LENGTH_LONG).show()
                            }
                        },
                        Response.ErrorListener {
                            Log.d("paramserror", it.message.toString())
                        }
                    ){
                        override fun getParams(): MutableMap<String, String> {
                            return hashMapOf("username" to username, "fullName" to fullName,
                                "password" to password, "vaccine_doses" to vaccine_doses.toString())
                        }
                    }
                    queue.add(stringRequest)
                } else{
                    Toast.makeText(this, "Password and Confirm Password are not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quit Registration")
        builder.setMessage("Are you sure you want to leave this page?")
        builder.setPositiveButton("Leave Register", DialogInterface.OnClickListener { dialogInterface, i ->
            finish()
        })
        builder.setNegativeButton("Cancel",null)
        builder.create().show()


    }
}