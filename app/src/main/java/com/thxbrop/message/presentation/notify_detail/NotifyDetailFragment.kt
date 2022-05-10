package com.thxbrop.message.presentation.notify_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thxbrop.message.databinding.FragmentNotifyDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotifyDetailFragment : Fragment() {
    private lateinit var binding: FragmentNotifyDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotifyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            fragmentNotifyDetailToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}