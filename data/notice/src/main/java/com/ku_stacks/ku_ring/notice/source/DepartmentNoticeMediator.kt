package com.ku_stacks.ku_ring.notice.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ku_stacks.ku_ring.local.entity.NoticeEntity
import com.ku_stacks.ku_ring.local.room.NoticeDao
import com.ku_stacks.ku_ring.notice.api.NoticeClient
import com.ku_stacks.ku_ring.notice.api.response.DepartmentNoticeResponse
import com.ku_stacks.ku_ring.notice.mapper.toEntityList
import com.ku_stacks.ku_ring.preferences.PreferenceUtil
import com.ku_stacks.ku_ring.util.DateUtil
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class DepartmentNoticeMediator(
    private val shortName: String,
    private val noticeClient: NoticeClient,
    private val noticeDao: NoticeDao,
    private val preferences: PreferenceUtil,
) : RemoteMediator<Int, NoticeEntity>() {

    private val pageNumberMap = hashMapOf<PageKey, Int>()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NoticeEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> getRefreshKey(state) ?: 0
            LoadType.PREPEND -> getPrependKey(state)
            LoadType.APPEND -> getAppendKey(state)
        }
        Timber.d("Load dept notices: $shortName, $loadType, $page")

        if (page == null || page < 0) {
            Timber.d("Dept notices skip: $shortName, $loadType, $page")
            return MediatorResult.Success(endOfPaginationReached = page != null)
        }

        return try {
            // TODO: 나중에 중요 공지 보여줄 때 주석 해제하기
//            if (page == 0) {
//                loadAndSaveImportantNotices()
//            }
            val noticeResponse = noticeClient.fetchDepartmentNoticeList(
                shortName = shortName,
                page = page,
                size = itemSize
            )
            Timber.d("Loaded dept notices: ${noticeResponse.data.lastOrNull()?.articleId}")
            insertNotices(noticeResponse.data, page)

            val isPageEnd = noticeResponse.data.isEmpty()
            if (isPageEnd) {
                Timber.d("Dept notices page end: $shortName, $loadType, $page")
            }
            MediatorResult.Success(endOfPaginationReached = isPageEnd)
        } catch (e: Exception) {
            Timber.e("Dept notices exception: ${e.message}")
            MediatorResult.Error(e)
        }
    }

    private suspend fun loadAndSaveImportantNotices() {
        val importNoticesResponse = noticeClient.fetchDepartmentNoticeList(
            shortName = shortName,
            page = 0,
            size = itemSize,
            important = true
        )
        insertNotices(importNoticesResponse.data, 0)
    }

    private suspend fun insertNotices(notices: List<DepartmentNoticeResponse>, page: Int) {
        val startDate = getAppStartedDate()
        val noticeEntities = notices.toEntityList(shortName, startDate)
        noticeEntities.map {
            pageNumberMap[PageKey(it.articleId, shortName)] = page
        }
        noticeDao.insertDepartmentNotices(noticeEntities)
    }

    private fun getAppStartedDate(): String {
        // TODO: NoticeRepositoryImpl.transformRemoteData()에도 있는 이 로직을 PrefUtil로 옮기기
        return preferences.startDate?.takeIf { it.isNotEmpty() } ?: DateUtil.getToday().apply {
            preferences.startDate = this
        }
    }

    private fun getRefreshKey(state: PagingState<Int, NoticeEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.articleId?.let { articleId ->
                pageNumberMap[PageKey(articleId, shortName)]
            }
        }
    }

    private fun getPrependKey(state: PagingState<Int, NoticeEntity>): Int? {
        val firstItem = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
        return firstItem?.let {
            pageNumberMap[PageKey(it.articleId, shortName)]?.minus(1)
        }
    }

    private fun getAppendKey(state: PagingState<Int, NoticeEntity>): Int? {
        val lastItem = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
        return lastItem?.let {
            // page number가 존재하지 않는 공지: 이전 실행에서 로드된 공지
            // 따라서 page size를 통해 간접적으로 page key를 계산해야 한다.
            (pageNumberMap[PageKey(it.articleId, shortName)] ?: state.pages.size).plus(1)
        }
    }

    companion object {
        const val itemSize = 20
    }
}

private data class PageKey(
    val articleId: String,
    val shortName: String,
)