package com.thxbrop.message.components

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.thxbrop.message.R
import com.thxbrop.message.extensions.dp

class AlertDialog(
    parentView: ConstraintLayout
) : Dialog(parentView.context, R.style.BaseDialog) {
    private var title: TextView
    private var subtitle: TextView
    private var negative: Button
    private var positive: Button
    private var editText: TextInputEditText
    private var editLayout: TextInputLayout

    private var itemView: View =
        LayoutInflater.from(context).inflate(R.layout.dialog_alert, parentView, false)

    init {
        title = itemView.findViewById(R.id.dialog_alert_title)
        subtitle = itemView.findViewById(R.id.dialog_alert_subtitle)
        negative = itemView.findViewById(R.id.dialog_alert_negative)
        positive = itemView.findViewById(R.id.dialog_alert_positive)
        editText = itemView.findViewById(R.id.dialog_alert_text_input_edittext)
        editLayout = itemView.findViewById(R.id.dialog_alert_text_input_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (itemView.layoutParams as ConstraintLayout.LayoutParams).apply {
            marginStart = 16.dp
            marginEnd = 16.dp
        }
        setContentView(itemView)
        setCancelable(false)
        window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    @MainThread
    fun setTitle(title: String) {
        this.title.text = title
    }

    @MainThread
    fun setSubTitle(subtitle: String) {
        this.subtitle.text = subtitle
    }

    @MainThread
    fun setNegativeText(text: String) {
        this.negative.text = text
    }

    @MainThread
    fun setPositiveText(text: String) {
        this.positive.text = text
    }

    @MainThread
    fun setNegativeClickListener(listener: () -> Unit) {
        negative.setOnClickListener {
            listener.invoke()
        }
    }

    @MainThread
    fun setPositiveClickListener(listener: () -> Unit) {
        positive.setOnClickListener {
            listener.invoke()
        }
    }

    @MainThread
    fun setEdittext(hint: String, onTextChanged: (Editable?) -> Unit) {
        editLayout.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        editLayout.hint = hint
        editText.addTextChangedListener(afterTextChanged = onTextChanged)
    }
}