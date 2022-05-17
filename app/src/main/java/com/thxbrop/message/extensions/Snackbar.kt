package com.thxbrop.message.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.snack(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
}