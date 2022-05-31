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
import com.thxbrop.message.data.remote.ServerException
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
                showMakeSureInformationDialog(email, password)
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
                            findNavController().popBackStack()
                            binding.isEnable(true)
                        }
                        is Resource.Failure -> {
                            when (resource.code) {
                                ServerException.LOGIN_EMAIL_EXIST.code -> {
                                    val email =
                                        binding.fragmentLoginTextInputEdittext.text.toString()
                                    val password =
                                        binding.fragmentLoginTextInputEdittext2.text.toString()
                                    showRegisterDialog(
                                        email,
                                        password,
                                        getString(R.string.error_502)
                                    )
                                }
                                ServerException.WRONG_PASSWORD.code -> {
                                    binding.fragmentLoginCoordinator.snack(R.string.error_513)
                                }
                                else -> binding.fragmentLoginCoordinator.snack(resource.message)
                            }
                            binding.isEnable(true)
                        }
                    }
                }
            }
            lifecycleScope.launchWhenStarted {
                registerFlow.collectLatest { resource ->
                    when (resource) {
                        Resource.Loading -> {
                            binding.isEnable(false)
                        }
                        is Resource.Success -> {
                            findNavController().popBackStack()
                            binding.isEnable(true)
                        }
                        is Resource.Failure -> {
                            when (resource.code) {

                            }
                            binding.isEnable(true)
                        }
                    }
                }
            }
        }
    }

    private fun showMakeSureInformationDialog(email: String, password: String) {
        AlertDialog(binding.root).apply {
            setTitle(getString(R.string.dialog_alert_make_sure_info_title))
            setSubTitle(email)
            setNegativeText(getString(R.string.dialog_alert_make_sure_info_negative))
            setNegativeClickListener {
                dismiss()
            }
            setPositiveText(getString(R.string.dialog_alert_make_sure_info_positive))
            setPositiveClickListener {
                dismiss()
                viewModel.login(email, password)
            }
            show()
        }

    }

    private fun showRegisterDialog(
        email: String,
        password: String,
        title: String
    ) {
        AlertDialog(binding.root).apply {
            var edit: String? = null
            setTitle(title)
            setSubTitle(getString(R.string.dialog_alert_register_subtitle))
            setEdittext(getString(R.string.dialog_alert_register_hint)) {
                edit = it.toString()
            }
            setNegativeText(getString(R.string.dialog_alert_register_negative))
            setNegativeClickListener {
                dismiss()
            }
            setPositiveText(getString(R.string.dialog_alert_register_positive))
            setPositiveClickListener {
                dismiss()
                viewModel.register(email, password, edit ?: "")
            }
            show()
        }
    }
}