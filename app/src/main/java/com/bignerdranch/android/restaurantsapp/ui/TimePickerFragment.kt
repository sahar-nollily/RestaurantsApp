package com.bignerdranch.android.restaurantsapp.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.bignerdranch.android.restaurantsapp.data.PlacesDetail
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TimePickerFragment(val placesDetail: PlacesDetail) : DialogFragment(){
    interface Callbacks {
        fun onTimeSelected(hourOfDay: Int,minute: Int,placesDetail:PlacesDetail)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val timeListener =  TimePickerDialog.OnTimeSetListener {
            _: TimePicker, hourOfDay : Int, minute : Int ->
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onTimeSelected(hourOfDay,minute,placesDetail)
            }
        }
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog (context,
                timeListener,
                hour,
                minute,
                DateFormat.is24HourFormat(context))
    }
}
