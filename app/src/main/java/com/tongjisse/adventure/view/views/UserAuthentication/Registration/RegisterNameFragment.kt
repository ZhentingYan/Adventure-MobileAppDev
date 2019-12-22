package com.tongjisse.adventure.view.views.UserAuthentication.Registration

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tongjisse.adventure.R
import kotlinx.android.synthetic.main.fragment_register_name.*


/**
 * Created by Owner on 2017-06-09.
 */

class RegisterNameFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_name, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Check text changes in EditText
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                registrationProceed()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // no need here
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // no need here
            }
        }
        etFirstName.addTextChangedListener(textWatcher)
        etLastName.addTextChangedListener(textWatcher)
    }


    //Method to check if users can proceed based on the results of the EditTexts
    fun registrationProceed() {
        if (etFirstName.text.toString().isEmpty() || etLastName.text.toString().isEmpty()) {
            bRegProceed.isEnabled = false
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
        } else {
            bRegProceed.isEnabled = true
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
            bRegProceed.setOnClickListener {
                FIRST_NAME = etFirstName.text.toString()
                LAST_NAME = etLastName.text.toString()
                val fragmentManager = fragmentManager
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                val registerEmailFragment = RegisterEmailFragment()
                fragmentTransaction.replace(R.id.progressFragment, registerEmailFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    companion object {
        lateinit var FIRST_NAME: String
        lateinit var LAST_NAME: String
    }


}
