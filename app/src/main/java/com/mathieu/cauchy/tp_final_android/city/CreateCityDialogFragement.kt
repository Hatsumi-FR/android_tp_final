package com.mathieu.cauchy.tp_final_android.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.mathieu.cauchy.tp_final_android.R

class CreateCityDialogFragement : DialogFragment() {
    interface CreateCityDialogListener {
        fun onDialogPositiveClick(cityName: String)
        fun onDialogNegativeClick()
    }

    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val input = EditText(context)
        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.createcity_hint)
            builder.setTitle(context.getString(R.string.createcity_title))
                .setView(input)
                .setPositiveButton(context.getString(R.string.createcity_positive), DialogInterface.OnClickListener { _, _ ->
                    listener?.onDialogPositiveClick(input.text.toString())
                }).setNegativeButton(context.getString(R.string.createcity_negative), DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                    listener?.onDialogNegativeClick()
                })
        }
        return builder.create()
    }
}