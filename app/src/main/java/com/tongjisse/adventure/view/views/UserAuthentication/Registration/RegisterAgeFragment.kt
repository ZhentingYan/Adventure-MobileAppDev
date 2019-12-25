package com.tongjisse.adventure.view.views.UserAuthentication.Registration

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tongjisse.adventure.R
import kotlinx.android.synthetic.main.fragment_register_age.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ZhentingYan（UserAuthentication Module）
 */
class RegisterAgeFragment : Fragment() {
    internal lateinit var newDate: Calendar
    internal lateinit var simpleDateFormat: SimpleDateFormat
    internal lateinit var date: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_age, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //DatePickerDialog
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -16)


        etAge.setOnClickListener {
            val datePickerDialog = DatePickerDialog(activity!!, 0, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                date = simpleDateFormat.format(newDate.time)
                etAge.setText(date)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis
            datePickerDialog.show()
        }

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
        etAge.addTextChangedListener(textWatcher)
    }

    fun registrationProceed() {
        if (!etAge.text.toString().isEmpty()) {
            bRegProceed.isEnabled = true
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
            bRegProceed.setOnClickListener {
                val today = Calendar.getInstance()

                val diff = today.get(Calendar.YEAR) - newDate.get(Calendar.YEAR)
                //Use Jodatime for specific age difference
                AGE = diff
                Log.d("blue", Integer.toString(AGE))
                val fragmentManager = fragmentManager
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.progressFragment, RegisterAvatarFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        } else {
            bRegProceed.isEnabled = false
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail)
            bRegProceed.setTextColor(Color.parseColor("#ff6666"))
        }
    }

    companion object {
        var AGE: Int = 0
    }

}
