package com.ku_stacks.ku_ring.main.notice.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ku_stacks.ku_ring.designsystem.components.DoubleTapBackHandler
import com.ku_stacks.ku_ring.designsystem.components.LightAndDarkPreview
import com.ku_stacks.ku_ring.designsystem.kuringtheme.KuringTheme
import com.ku_stacks.ku_ring.domain.Notice
import com.ku_stacks.ku_ring.main.notice.compose.components.NoticeScreenHeader
import com.ku_stacks.ku_ring.main.notice.compose.inner_screen.NoticeTabScreens

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NoticeScreen(
    onSearchIconClick: () -> Unit,
    onNotificationIconClick: () -> Unit,
    onNoticeClick: (Notice) -> Unit,
    onNavigateToEditDepartment: () -> Unit,
    onBackSingleTap: () -> Unit,
    onBackDoubleTap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DoubleTapBackHandler(
        enabled = true,
        tapInterval = 2000L,
        onSingleTap = onBackSingleTap,
        onDoubleTap = onBackDoubleTap,
    )

    Column(modifier = modifier) {
        NoticeScreenHeader(
            onSearchIconClick = onSearchIconClick,
            onNotificationIconClick = onNotificationIconClick,
            modifier = Modifier.fillMaxWidth(),
        )
        NoticeTabScreens(
            onNoticeClick = onNoticeClick,
            onNavigateToEditDepartment = onNavigateToEditDepartment,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
    }
}


@LightAndDarkPreview
@Composable
private fun NoticeScreenPreview() {
    KuringTheme {
        NoticeScreen(
            onSearchIconClick = {},
            onNotificationIconClick = {},
            onNoticeClick = {},
            onNavigateToEditDepartment = {},
            onBackSingleTap = {},
            onBackDoubleTap = {},
            modifier = Modifier
                .fillMaxSize()
                .background(KuringTheme.colors.background),
        )
    }
}