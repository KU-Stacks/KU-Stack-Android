package com.ku_stacks.ku_ring.ui.main.search.fragment_notice

import androidx.recyclerview.widget.RecyclerView
import com.ku_stacks.ku_ring.databinding.ItemNoticeBinding
import com.ku_stacks.ku_ring.domain.Notice

class SearchNoticeViewHolder(
    private val binding: ItemNoticeBinding,
    private val itemClick: (Notice) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(notice: Notice) {
        binding.noticeItem = notice
        binding.root.setOnClickListener {
            itemClick(notice)
        }
        binding.executePendingBindings()
    }
}