package com.thxbrop.message.extensions

import android.widget.TextView
import androidx.annotation.StringRes
import com.thxbrop.message.application

fun TextView.setTextColorResource(@StringRes resId: Int) {
    text = application.getText(resId)
}