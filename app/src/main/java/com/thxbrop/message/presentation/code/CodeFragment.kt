package com.thxbrop.message.presentation.code

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import com.thxbrop.message.databinding.FragmentCodeBinding

class CodeFragment : Fragment() {
    private lateinit var binding: FragmentCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val buttons = arrayOf(
                fragmentLoginButton1,
                fragmentLoginButton2,
                fragmentLoginButton3,
                fragmentLoginButton4,
                fragmentLoginButton5,
                fragmentLoginButton6,
                fragmentLoginButton7,
                fragmentLoginButton8,
                fragmentLoginButton9,
                fragmentLoginButtonPlus,
                fragmentLoginButton0,
                fragmentLoginButtonMinus
            )
            val keys = arrayOf(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "0", "-"
            )
            val values = arrayOf(
                "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ", "", "", "删除"
            )
            repeat(12) { index ->
                with(buttons[index]) {
                    itemNumberButtonNumber.text = keys[index]
                    itemNumberButtonText.text = values[index]
                    root.setOnClickListener {
                        it.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS)
                        append(keys[index])
                    }
                }
            }
        }
    }

    private fun append(s: CharSequence) {
        val text =
            binding.fragmentCodeEdit.text ?: Editable.Factory.getInstance()
                .newEditable("")
        if (s == "-") {
            if (text.isEmpty()) return
            binding.fragmentCodeEdit.text =
                Editable.Factory.getInstance().newEditable(text.take(text.length - 1))
        } else {
            text.append(s)
            binding.fragmentCodeEdit.text = text
        }

    }
}