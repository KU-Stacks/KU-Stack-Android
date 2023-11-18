package com.ku_stacks.ku_ring.main.notice

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ku_stacks.ku_ring.main.notice.category.NoticesChildFragment
import com.ku_stacks.ku_ring.main.notice.department.DepartmentNoticeFragment

class NoticesPagerAdapter(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc) {

    /**
     * lambda로 하지 않으면 memory leak 발생
     * (Fragment 가 destroy 될 때 참조가 남음)
     */
    private val items = listOf("dep", "bch", "sch", "emp", "nat", "stu", "ind", "nor", "lib")
        .map { shortCategory ->
            if (shortCategory == "dep") {
                { DepartmentNoticeFragment() }
            } else {
                { NoticesChildFragment.newInstance(shortCategory) }
            }
        }

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment = items[position].invoke()
}