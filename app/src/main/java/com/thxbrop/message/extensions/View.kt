package com.thxbrop.message.extensions

import android.content.res.Resources
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.StringRes
import com.thxbrop.message.application

fun TextView.setTextColorResource(@StringRes resId: Int) {
    text = application.getText(resId)
}

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

val Int.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(),
        Resources.getSystem().displayMetrics
    )