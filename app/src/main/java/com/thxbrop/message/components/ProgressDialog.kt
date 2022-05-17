package com.thxbrop.message.components

import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.thxbrop.message.R

class ProgressDialog(fragmentActivity: FragmentActivity) : Dialog(fragmentActivity) {
    private lateinit var progressIndicator: LinearProgressIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        progressIndicator = findViewById(R.id.progress)
    }

    fun submit(progress: Int) {
        progressIndicator.setProgress(progress, true)
    }
}
