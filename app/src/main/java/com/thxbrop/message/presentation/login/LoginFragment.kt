package com.thxbrop.message.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.thxbrop.message.R
import com.thxbrop.message.Resource
import com.thxbrop.message.components.AlertDialog
import com.thxbrop.message.databinding.FragmentLoginBinding
import com.thxbrop.message.extensions.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
            fragmentLoginFloating.setOnClickListener {
                val email = fragmentLoginTextInputEdittext.text.toString()
                val password = fragmentLoginTextInputEdittext2.text.toString()
                try {
                    AlertDialog(binding.root).apply {
                        setTitle(getString(R.string.dialog_alert_title))
                        setSubTitle(email)
                        setNegativeText(getString(R.string.dialog_alert_negative))
                        setNegativeClickListener {
                            dismiss()
                        }
                        setPositiveText(getString(R.string.dialog_alert_positive))
                        setPositiveClickListener {
                            dismiss()
                            viewModel.login(email, password)
                        }
                        show()
                    }
                } catch (e: Exception) {
                    snack(e.message ?: "error")
                }
            }
        }
        with(viewModel) {
            fun FragmentLoginBinding.isEnable(enabled: Boolean) {
                fragmentLoginFloating.isEnabled = enabled
            }
            lifecycleScope.launchWhenStarted {
                loginFlow.collectLatest { resource ->
                    when (resource) {
                        Resource.Loading -> {
                            binding.isEnable(false)
                        }
                        is Resource.Success -> {
                            snack("success")
                            findNavController().popBackStack()
                            binding.isEnable(true)
                        }
                        is Resource.Failure -> {
                            snack(resource.message)
                            binding.isEnable(true)
                        }
                    }
                }
            }
        }
    }
}