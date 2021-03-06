package com.example.studentscompanion.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.studentscompanion.R
import kotlinx.android.synthetic.main.custom_progressbar.view.*

class ProgressDialogBox {

    companion object{

        fun showDialog(context: Context, message:String, isCancellable:Boolean): AlertDialog {
            val inflater= LayoutInflater.from(context)
            val view=inflater.inflate(R.layout.custom_progressbar,null)
            view.tvprogressbar.text=message
            val alertDialog= AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(isCancellable)
                .create()

            return alertDialog
        }

        fun dialogWithPosNegBtn(context: Context, message:String, isCancellable:Boolean): AlertDialog.Builder{
            val alertDialog= AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(isCancellable)

            return alertDialog
        }

    }
}