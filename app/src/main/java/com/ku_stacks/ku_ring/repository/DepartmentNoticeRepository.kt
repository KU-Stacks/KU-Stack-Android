package com.ku_stacks.ku_ring.repository

import androidx.paging.PagingData
import com.ku_stacks.ku_ring.data.model.Notice
import kotlinx.coroutines.flow.Flow

interface DepartmentNoticeRepository {
    fun getDepartmentNotices(shortName: String): Flow<PagingData<Notice>>
}