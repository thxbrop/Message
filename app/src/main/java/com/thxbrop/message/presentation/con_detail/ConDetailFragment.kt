package com.thxbrop.message.presentation.con_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thxbrop.message.databinding.FragmentConversationDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConDetailFragment : Fragment() {
    private lateinit var binding: FragmentConversationDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConversationDetailBinding.inflate(inflater, container, false)
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