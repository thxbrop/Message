package com.thxbrop.message.presentation.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.thxbrop.message.R
import com.thxbrop.message.databinding.FragmentMessageBinding
import com.thxbrop.message.databinding.ItemMessageInBinding
import com.thxbrop.message.domain.model.Message
import com.thxbrop.message.presentation.PageController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : Fragment(), PageController<Message> {
    private lateinit var binding: FragmentMessageBinding

    private var conversationId: Int? = null

    companion object {
        const val BUNDLE_NOTIFY_ID = "notifyId"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        arguments?.let {
            conversationId = it.getInt(BUNDLE_NOTIFY_ID)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            fragmentMessageToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            fragmentMessageToolbar.setOnClickListener {
                findNavController().navigate(R.id.action_messageFragment_to_notifyDetailFragment)
            }
            val live = MutableLiveData<List<Message>>()
            live.observe(viewLifecycleOwner) {
                submitList(it)
            }
            fragmentMessageRecyclerview.linear(
                reverseLayout = true
            ).setup {
                addType<Message>(R.layout.item_message_in)
                setAnimation(AnimationType.SLIDE_BOTTOM)
                onBind {
                    val binding = ItemMessageInBinding.bind(itemView)
                    val model = getModel<Message>()
                    with(binding) {
                        itemMessageTitle.text = model.createdAt.toString()
                        itemMessageSubtitle.text = model.content
                    }
                }
            }
        }
    }

    override fun animateTitle(title: String) {
        TODO("Not yet implemented")
    }

    override fun setTitle(title: String) {
        TODO("Not yet implemented")
    }

    override fun submitList(list: List<Message>) {
        binding.fragmentMessageRecyclerview.models = list
    }
}