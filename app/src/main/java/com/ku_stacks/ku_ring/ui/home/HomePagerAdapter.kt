package com.ku_stacks.ku_ring.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ku_stacks.ku_ring.ui.home.category.stage0_Bachelor.BachelorFragment
import com.ku_stacks.ku_ring.ui.home.category.stage1_Scholarship.ScholarshipFragment
import com.ku_stacks.ku_ring.ui.home.category.stage2_employ.EmployFragment
import com.ku_stacks.ku_ring.ui.home.category.stage3_nation.NationFragment
import com.ku_stacks.ku_ring.ui.home.category.stage4_Student.StudentFragment
import com.ku_stacks.ku_ring.ui.home.category.stage5_Industry.IndustryFragment
import com.ku_stacks.ku_ring.ui.home.category.stage6_Nornal.NormalFragment
import com.ku_stacks.ku_ring.ui.home.category.stage7_Library.LibraryFragment

class HomePagerAdapter(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc) {

    /**
     * array 의 내부를 lambda 로 하지않으면 memory leak 발생
     * (Fragment 가 destroy 될 때 참조가 남음)
     */
    private val items = arrayOf(
        { BachelorFragment() },
        { ScholarshipFragment() },
        { EmployFragment() },
        { NationFragment() },
        { StudentFragment() },
        { IndustryFragment() },
        { NormalFragment() },
        { LibraryFragment() }
    )

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment = items[position].invoke()
}