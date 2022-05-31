package com.thxbrop.message.extensions

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.thxbrop.message.application

fun Fragment.snack(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snack(@StringRes resId: Int) {
    Snackbar.make(requireView(), application.getString(resId), Snackbar.LENGTH_SHORT).show()
}

fun View.snack(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun View.snack(@StringRes resId: Int) {
    Snackbar.make(this, application.getString(resId), Snackbar.LENGTH_SHORT).show()
}