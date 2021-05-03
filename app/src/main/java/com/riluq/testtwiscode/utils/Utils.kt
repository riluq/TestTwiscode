package com.riluq.testtwiscode.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.riluq.testtwiscode.R


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.setTextColorResource(context: Context, idColor: Int) {
    setTextColor(context.resources.getColor(idColor))
}

fun AppCompatButton.setButtonEnable(context: Context) {
    setTextColor(context.resources.getColor(android.R.color.black))
    background = context.resources.getDrawable(R.drawable.background_button_enable)
    isEnabled = true
}
fun AppCompatButton.setButtonGreen(context: Context) {
    setTextColor(context.resources.getColor(android.R.color.white))
    background = context.resources.getDrawable(R.drawable.background_button_green)
    isEnabled = true
}
fun AppCompatButton.setButtonDisable(context: Context) {
    setTextColor(context.resources.getColor(android.R.color.white))
    background = context.resources.getDrawable(R.drawable.background_button_disable)
    isEnabled = false
}

fun AppCompatButton.setButtonCancel(context: Context) {
    setTextColor(context.resources.getColor(android.R.color.white))
    background = context.resources.getDrawable(R.drawable.background_button_cancel)
    isEnabled = true
}
