package com.thxbrop.message.presentation.notify

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.SharedElementCallback
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.material.transition.MaterialFadeThrough
import com.thxbrop.message.R
import com.thxbrop.message.Resource
import com.thxbrop.message.databinding.FragmentNotifyBinding
import com.thxbrop.message.databinding.ItemNotifyBinding
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.extensions.setTextColorResource
import com.thxbrop.message.presentation.PageController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotifyFragment : Fragment(), PageController<Conversation> {
    private lateinit var binding: FragmentNotifyBinding
    private val viewModel by viewModels<NotifyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reenterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
    }

    companion object {
        private const val TRANS_ITEM_ROOT = "item_root"
        private const val TRANS_ITEM_IMAGE = "item_image"
        private const val TRANS_ITEM_TITLE = "item_title"
    }

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
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
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
                addType<Conversation>(R.layout.item_notify)
                var binding: ItemNotifyBinding
                onBind {
                    val conversation = getModel<Conversation>()
                    binding = ItemNotifyBinding.bind(itemView)
                    with(binding) {
                        itemNotifyTitle.text = conversation.name
                    }
                }
                onClick(R.id.item_message_item_view) {
                    binding = ItemNotifyBinding.bind(itemView)
                    itemView.transitionName = TRANS_ITEM_ROOT
                    binding.itemNotifyTitle.transitionName = TRANS_ITEM_TITLE
                    binding.itemNotifyImage.transitionName = TRANS_ITEM_IMAGE
                    setExitSharedElementCallback(object : SharedElementCallback() {
                        override fun onMapSharedElements(
                            names: MutableList<String>,
                            sharedElements: MutableMap<String, View>
                        ) {
                            with(binding) {
                                sharedElements[itemView.transitionName] = itemView
                                sharedElements[itemNotifyTitle.transitionName] = itemNotifyTitle
                                sharedElements[itemNotifyImage.transitionName] = itemNotifyImage
                            }
                        }
                    })
                    val conversation = getModel<Conversation>()
                    findNavController().navigate(
                        R.id.action_notifyFragment_to_messageFragment,
                        bundleOf(
                            "conversationId" to conversation.name,
                        ),
                        NavOptions.Builder()
                            .setRestoreState(true)
                            .build(),
                        with(binding) {
                            FragmentNavigatorExtras(
                                itemNotifyImage to itemNotifyImage.transitionName,
                                itemNotifyTitle to itemNotifyTitle.transitionName,
                                itemView to itemView.transitionName
                            )
                        }
                    )
                }
                onLongClick(R.id.item_message_item_view) {
                    Toast.makeText(
                        requireContext(),
                        getModel<Conversation>().name,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                conversationsState.collectLatest { resource ->
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

    override fun submitList(list: List<Conversation>) {
        binding.fragmentNotifyRecyclerview.models = list
    }

    override fun onStart() {
        super.onStart()
        viewModel.getConversations()
    }
}