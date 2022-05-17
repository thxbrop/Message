package com.thxbrop.message.presentation.notify

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.thxbrop.message.R
import com.thxbrop.message.Resource
import com.thxbrop.message.databinding.FragmentNotifyBinding
import com.thxbrop.message.databinding.ItemNotifyBinding
import com.thxbrop.message.domain.model.Notify
import com.thxbrop.message.extensions.setTextColorResource
import com.thxbrop.message.presentation.PageController
import com.thxbrop.message.presentation.message.MessageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotifyFragment : Fragment(), PageController<Notify> {
    private lateinit var binding: FragmentNotifyBinding
    private val viewModel by viewModels<NotifyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            fragmentNotifyToolbarTextSwitcher.setFactory {
                TextView(requireContext()).also {
                    it.typeface = Typeface.DEFAULT_BOLD
                    it.setTextColorResource(R.color.textColor)
                    it.textSize = 18f
                }
            }
            fragmentNotifyToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_notifyFragment_to_loginFragment)
            }
            fragmentNotifyRecyclerview.linear().setup {
                addType<Notify>(R.layout.item_notify)
                var binding: ItemNotifyBinding
                onBind {
                    val notify = getModel<Notify>()
                    binding = ItemNotifyBinding.bind(itemView)
                    with(binding) {
                        itemNotifyTitle.text = notify.name
                    }
                }
                onClick(R.id.item_message_item_view) {
                    binding = ItemNotifyBinding.bind(itemView)
                    val notify = getModel<Notify>()
                    findNavController().navigate(
                        R.id.action_notifyFragment_to_messageFragment,
                        bundleOf(
                            MessageFragment.BUNDLE_NOTIFY_ID to notify.id,
                        )
                    )
                }
                onLongClick(R.id.item_message_item_view) {
                    Toast.makeText(
                        requireContext(),
                        getModel<Notify>().name,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                conversationsFlow.collectLatest { resource ->
                    when (resource) {
                        Resource.Loading -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            submitList(resource.data)
                        }
                        is Resource.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                resource.exception.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        setTitle()
    }

    override fun animateTitle(title: String) {
        binding.fragmentNotifyToolbarTextSwitcher.setText(title)
    }

    override fun setTitle(title: String) {
        binding.fragmentNotifyToolbarTextSwitcher.setCurrentText(title)
    }

    override fun submitList(list: List<Notify>) {
        binding.fragmentNotifyRecyclerview.models = list
    }

    override fun onStart() {
        super.onStart()
        viewModel.getNotifies()
    }
}