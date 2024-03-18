package com.ku_stacks.ku_ring.main.search.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ku_stacks.ku_ring.designsystem.components.CenterTitleTopBar
import com.ku_stacks.ku_ring.designsystem.components.LightAndDarkPreview
import com.ku_stacks.ku_ring.designsystem.components.SearchTextField
import com.ku_stacks.ku_ring.designsystem.kuringtheme.KuringTheme
import com.ku_stacks.ku_ring.designsystem.kuringtheme.values.Pretendard
import com.ku_stacks.ku_ring.designsystem.utils.NoRippleInteractionSource
import com.ku_stacks.ku_ring.domain.Notice
import com.ku_stacks.ku_ring.domain.Staff
import com.ku_stacks.ku_ring.main.R
import com.ku_stacks.ku_ring.main.search.SearchViewModel
import com.ku_stacks.ku_ring.main.search.compose.inner_screen.NoticeSearchScreen
import com.ku_stacks.ku_ring.main.search.compose.inner_screen.StaffSearchScreen
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigationClick: () -> Unit,
    onClickNotice: (Notice) -> Unit,
    modifier: Modifier = Modifier,
    searchState: SearchState = rememberSearchState(),
    tabPages: List<SearchTabInfo> = SearchTabInfo.values().toList()
) {
    val noticeList by viewModel.noticeSearchResult.collectAsState(initial = emptyList())
    val staffList by viewModel.staffSearchResult.collectAsState(initial = emptyList())

    SearchScreen(
        onNavigationClick = onNavigationClick,
        onClickSearch = { viewModel.onClickSearch(it) },
        onClickNotice = onClickNotice,
        searchState = searchState,
        tabPages = tabPages,
        noticeList = noticeList,
        staffList = staffList,
        modifier = modifier,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchScreen(
    onNavigationClick: () -> Unit,
    onClickSearch: (SearchState) -> Unit,
    onClickNotice: (Notice) -> Unit,
    searchState: SearchState,
    tabPages: List<SearchTabInfo>,
    noticeList: List<Notice>,
    staffList: List<Staff>,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(KuringTheme.colors.background)
    ) {
        CenterTitleTopBar(
            title = stringResource(id = R.string.search),
            navigation = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = KuringTheme.colors.gray600,
                )
            },
            onNavigationClick = { onNavigationClick() },
            action = ""
        )

        SearchTextField(
            query = searchState.query,
            onQueryUpdate = { searchState.query = it },
            placeholderText = stringResource(id = R.string.search_enter_keyword),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onClickSearch(searchState)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 0.dp)
        )

        SearchResultTitle()

        val pagerState = rememberPagerState(pageCount = { tabPages.size })

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.distinctUntilChanged()
                .collect { pageIndex ->
                    searchState.tab = SearchTabInfo.values()[pageIndex]
                }
        }

        SearchTabRow(
            pagerState = pagerState,
            tabPages = tabPages,
        )

        SearchResultHorizontalPager(
            searchState = searchState,
            pagerState = pagerState,
            tabPages = tabPages,
            noticeList = noticeList,
            staffList = staffList,
            onClickNotice = onClickNotice,
        )
    }
}

@Composable
fun rememberSearchState(
    query: String = "",
): SearchState {
    return remember {
        SearchState(
            query = query,
            tab = SearchTabInfo.Notice,
            isLoading = false,
        )
    }
}

@Composable
private fun SearchResultTitle(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = R.string.search_result),
        style = TextStyle(
            color = KuringTheme.colors.textCaption1,
            fontSize = 16.sp,
            fontFamily = Pretendard,
            lineHeight = 27.sp,
        ),
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 18.dp, start = 20.dp, bottom = 10.dp),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchTabRow(
    pagerState: PagerState,
    tabPages: List<SearchTabInfo>,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = KuringTheme.colors.gray100,
        indicator = {},
        divider = {},
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 6.dp)
            .clip(RoundedCornerShape(14.dp))
    ) {
        tabPages.forEachIndexed { index, searchTabInfo ->
            val isSelected = pagerState.currentPage == index
            val tabBackgroundColor =
                if (isSelected) KuringTheme.colors.background else Color.Transparent

            Tab(
                selected = isSelected,
                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                text = {
                    Text(
                        text = stringResource(id = searchTabInfo.titleResId),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = Pretendard,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                },
                unselectedContentColor = KuringTheme.colors.textCaption1,
                selectedContentColor = KuringTheme.colors.mainPrimary,
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 5.dp)
                    .background(color = tabBackgroundColor, shape = RoundedCornerShape(12.dp)),
                interactionSource = NoRippleInteractionSource()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchResultHorizontalPager(
    searchState: SearchState,
    pagerState: PagerState,
    tabPages: List<SearchTabInfo>,
    noticeList: List<Notice>,
    staffList: List<Staff>,
    onClickNotice: (Notice) -> Unit,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) { index ->
        when (tabPages[index]) {
            SearchTabInfo.Notice -> {
                NoticeSearchScreen(
                    searchState = searchState,
                    noticeList = noticeList,
                    onClickNotice = onClickNotice,
                )
            }

            SearchTabInfo.Staff -> {
                StaffSearchScreen(
                    searchState = searchState,
                    staffList = staffList
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun SearchScreenPreview() {
    KuringTheme {
        SearchScreen(
            searchState = rememberSearchState("산학협력"),
            onNavigationClick = {},
            onClickNotice = {},
            onClickSearch = {},
            noticeList = emptyList(),
            staffList = emptyList(),
            modifier = Modifier
                .background(KuringTheme.colors.background)
                .fillMaxSize(),
            tabPages = SearchTabInfo.values().toList(),
        )
    }
}
