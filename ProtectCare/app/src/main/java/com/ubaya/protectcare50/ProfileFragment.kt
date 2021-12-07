package com.ubaya.protectcare50

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.card_history.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*



/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val fullName = GlobalData.user.fullName
        val vaccine_doses = GlobalData.user.vaccine_doses

        view.textProfileName.text = fullName
        view.textVaccineDoses.text = vaccine_doses

        view.fabLogOut.setOnClickListener {


            val builder = AlertDialog.Builder(view.context)
            builder.setTitle("Log Out ProtectCare50")
            builder.setMessage("Do you want to log out from ProtectCare?")
            builder.setPositiveButton("Log out", DialogInterface.OnClickListener { dialogInterface, i ->
                activity?.finish()
            })
            builder.setNegativeButton("Cancel",null)
            builder.create().show()
        }
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}