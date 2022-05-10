package com.thxbrop.message.presentation.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.android.material.transition.MaterialFadeThrough
import com.thxbrop.message.R
import com.thxbrop.message.databinding.FragmentMessageBinding
import com.thxbrop.message.databinding.ItemMessageBinding
import com.thxbrop.message.domain.model.ConversationType
import com.thxbrop.message.domain.model.Message
import com.thxbrop.message.presentation.PageController
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MessageFragment : Fragment(), PageController<Message> {
    private lateinit var binding: FragmentMessageBinding

    private val callback = EnterSharedElementCallback()

    inner class EnterSharedElementCallback : SharedElementCallback() {
        override fun onMapSharedElements(
            names: MutableList<String>,
            sharedElements: MutableMap<String, View>
        ) {
            with(binding) {
                sharedElements[fragmentMessageToolbarImage.transitionName] =
                    fragmentMessageToolbarImage
                sharedElements[fragmentMessageToolbarTitle.transitionName] =
                    fragmentMessageToolbarTitle
                sharedElements[fragmentMessageAppbarLayout.transitionName] =
                    fragmentMessageAppbarLayout
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_elements)
        setEnterSharedElementCallback(callback)
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
            val conversationType = arguments?.getInt("conversationType") ?: ConversationType.CHAT
            fragmentMessageToolbarTitle.text = arguments?.getString("conversationId")
            fragmentMessageRecyclerview.linear(
                reverseLayout = true
            ).setup {
                addType<Message>(R.layout.item_message)
                setAnimation(AnimationType.SLIDE_BOTTOM)
                onBind {
                    val binding = ItemMessageBinding.bind(itemView)
                    val model = getModel<Message>()
                    with(binding) {
                        itemMessageTitle.text = model.createdAt.toString()
                        itemMessageSubtitle.text = model.content
                        when (conversationType) {
                            ConversationType.GROUP -> {}
                            else -> {
                                itemMessageImage.visibility = View.GONE
                            }
                        }
                    }
                }
            }
            fragmentMessageBottomSheetSend.setOnClickListener {
                val list = (live.value ?: emptyList()).toMutableList()
                list.add(Message(UUID.randomUUID().toString(), 0, System.currentTimeMillis()))
                live.value = list
            }

        }
        startPostponedEnterTransition()
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