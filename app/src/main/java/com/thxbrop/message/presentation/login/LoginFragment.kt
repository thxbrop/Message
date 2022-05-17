package com.thxbrop.message.presentation.login

import android.os.Bundle
import android.text.Editable
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.thxbrop.message.R
import com.thxbrop.message.Resource
import com.thxbrop.message.components.AlertDialog
import com.thxbrop.message.databinding.FragmentLoginBinding
import com.thxbrop.message.extensions.asPhoneNumber
import com.thxbrop.message.extensions.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            fragmentLoginToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            fragmentLoginTextInputEdittextPhone.addTextChangedListener {
                with((it?.toString() ?: "").asPhoneNumber()) {
                    fragmentLoginButton.text = if (countryPrefixOrNull == null) {
                        getString(R.string.fragment_login_button)
                    } else countryPrefix.c
                }
            }
            fragmentLoginFloating.setOnClickListener {
                val phoneNumber = fragmentLoginTextInputEdittextPhone.text.toString()
                if (phoneNumber.length < 13) {
                    snack(getString(R.string.fragment_login_illegal_phone))
                    return@setOnClickListener
                }
                try {
                    AlertDialog(binding.root).apply {
                        setTitle(getString(R.string.dialog_alert_title))
                        setSubTitle(phoneNumber.asPhoneNumber().formatted)
                        setNegativeText(getString(R.string.dialog_alert_negative))
                        setNegativeClickListener {
                            dismiss()
                        }
                        setPositiveText(getString(R.string.dialog_alert_positive))
                        setPositiveClickListener {
                            dismiss()
                            viewModel.loginByPhoneNumber(phoneNumber)
                        }
                        show()
                    }
                } catch (e: Exception) {
                    snack(e.message ?: "error")
                }
            }
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
        with(viewModel) {
            fun FragmentLoginBinding.isEnable(enabled: Boolean) {
                fragmentLoginFloating.isEnabled = enabled
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loginFlow.collectLatest { resource ->
                        when (resource) {
                            Resource.Loading -> {
                                binding.isEnable(false)
                            }
                            is Resource.Success -> {
                                findNavController().navigate(R.id.action_loginFragment_to_codeFragment)
                                binding.isEnable(true)
                            }
                            is Resource.Failure -> {
                                snack(resource.exception.message ?: "Unknown Error")
                                binding.isEnable(true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun append(s: CharSequence) {
        val text =
            binding.fragmentLoginTextInputEdittextPhone.text ?: Editable.Factory.getInstance()
                .newEditable("")
        if (s == "-") {
            if (text.isEmpty()) return
            binding.fragmentLoginTextInputEdittextPhone.text =
                Editable.Factory.getInstance().newEditable(text.take(text.length - 1))
        } else {
            text.append(s)
            binding.fragmentLoginTextInputEdittextPhone.text = text
        }

    }
}