package com.ku_stacks.ku_ring.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ku_stacks.ku_ring.designsystem.R
import com.ku_stacks.ku_ring.designsystem.theme.KuringTheme
import com.ku_stacks.ku_ring.designsystem.theme.Pretendard

/**
 * [CenterTitleTopBar]보다 더 넓은 공간을 차지하는 Top App Bar이다.
 *
 * 제목은 반드시 보여줘야 하며, 필요하다면 navigation 아이콘을 보여줄 수 있다.
 *
 * @param title 제목 문자열
 * @param modifier 적용할 [Modifier]
 * @param navigationIconId Navigation 아이콘의 drawable ID
 * @param onNavigationIconClick Navigation 아이콘을 클릭했을 때 실행할 콜백
 * @param iconDescription Navigation 아이콘을 설명하는 문자열. 접근성 서비스에 제공되는 값이다.
 */
@Composable
fun LargeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    @DrawableRes navigationIconId: Int? = null,
    onNavigationIconClick: (() -> Unit)? = null,
    iconDescription: String? = null,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .padding(start = 14.dp, top = 14.dp),
    ) {
        if (navigationIconId == null) {
            TransparentIconButton()
        } else {
            IconButton(
                onClick = { onNavigationIconClick?.invoke() },
                enabled = onNavigationIconClick != null,
            ) {
                Icon(
                    painter = painterResource(id = navigationIconId),
                    contentDescription = iconDescription,
                    tint = MaterialTheme.colors.onSurface,
                )
            }
        }

        LargeTopAppBarTitle(title = title)
    }
}

@Composable
private fun LargeTopAppBarTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 24.sp,
            lineHeight = 36.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(700),
            color = MaterialTheme.colors.onSurface,
        ),
        modifier = modifier.padding(start = 12.dp, top = 26.dp),
    )
}

@LightAndDarkPreview
@Composable
private fun LargeTopAppBarPreview_Icon() {
    KuringTheme {
        LargeTopAppBar(
            title = "학과를 추가하거나\n삭제할 수 있어요",
            modifier = Modifier.fillMaxWidth(),
            navigationIconId = R.drawable.ic_back,
        )
    }
}

@LightPreview
@Composable
private fun LargeTopAppBarPreview_NoIcon() {
    KuringTheme {
        LargeTopAppBar(
            title = "학과를 추가하거나\n삭제할 수 있어요",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}