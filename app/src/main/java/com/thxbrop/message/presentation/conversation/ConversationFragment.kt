package com.thxbrop.message.presentation.conversation

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.thxbrop.message.R
import com.thxbrop.message.data.local.TokenManager
import com.thxbrop.message.databinding.FragmentConversationBinding
import com.thxbrop.message.databinding.ItemConversationBinding
import com.thxbrop.message.domain.model.Conversation
import com.thxbrop.message.extensions.setTextColorResource
import com.thxbrop.message.extensions.snack
import com.thxbrop.message.presentation.PageController
import com.thxbrop.message.presentation.message.MessageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConversationFragment : Fragment(), PageController<Conversation> {
    private lateinit var binding: FragmentConversationBinding
    private val viewModel by viewModels<ConversationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConversationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            fragmentNotifyToolbarTextSwitcher.setFactory {
                TextView(requireContext()).also {
                    it.typeface = Typeface.DEFAULT_BOLD
                    it.setTextColorResource(R.color.black)
                    it.textSize = 18f
                }
            }
            fragmentNotifyToolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_notifyFragment_to_loginFragment)
            }
            fragmentNotifyRecyclerview.linear().setup {
                addType<Conversation>(R.layout.item_conversation)
                var binding: ItemConversationBinding
                onBind {
                    val notify = getModel<Conversation>()
                    binding = ItemConversationBinding.bind(itemView)
                    with(binding) {
                        itemNotifyTitle.text = notify.name
                    }
                }
                onClick(R.id.item_message_item_view) {
                    binding = ItemConversationBinding.bind(itemView)
                    val conversation = getModel<Conversation>()
                    findNavController().navigate(
                        R.id.action_notifyFragment_to_messageFragment,
                        bundleOf(
                            MessageFragment.BUNDLE_NOTIFY_ID to conversation.id,
                        )
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
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    uiState.collectLatest { state ->
                        submitList(state.conversations)
                        if (state.loading) {
                            snack("LOADING")
                        }
                        state.errorMsg?.let(::snack)
                    }
                }
            }
        }

        setTitle()
        if (TokenManager.hasCached) {
            viewModel.getConversations(TokenManager.userId)
            // TODO
        }
    }

    override fun animateTitle(title: String) =
        binding.fragmentNotifyToolbarTextSwitcher.setText(title)


    override fun setTitle(title: String) =
        binding.fragmentNotifyToolbarTextSwitcher.setCurrentText(title)


    override fun submitList(list: List<Conversation>) {
        binding.fragmentNotifyRecyclerview.models = list
    }
}