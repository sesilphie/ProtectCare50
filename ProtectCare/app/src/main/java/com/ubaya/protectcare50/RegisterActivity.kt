package com.ubaya.protectcare50

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quit Registration")
        builder.setMessage("Are you sure you want to leave this page?")
        builder.setPositiveButton("Yes, Leave the page", DialogInterface.OnClickListener { dialogInterface, i ->
            finish()
        })
        builder.setNegativeButton("Cancel",null)
        builder.create().show()


    }
}