package com.ku_stacks.ku_ring.ui.chat.viewholder

import com.ku_stacks.ku_ring.databinding.ItemChatSendBinding
import com.ku_stacks.ku_ring.ui.chat.ui_model.SentMessageUiModel
import com.ku_stacks.ku_ring.ui_util.visibleIf

class SendViewHolder(
    private val binding: ItemChatSendBinding
) : SealedChatViewHolder(binding.root) {

    fun bind(message: SentMessageUiModel, showDate: Boolean) {
        binding.sentMessageUiModel = message
        binding.chatDateTv.visibleIf(showDate)

        message.isPending.let { isPending ->
            binding.chatTimeTv.visibleIf(isPending == false)
            binding.chatSendErrorIv.visibleIf(isPending == null)
            binding.chatPendingProgressbar.visibleIf(isPending == true)
        }
    }
}