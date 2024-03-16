package com.ku_stacks.ku_ring.main.setting.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ku_stacks.ku_ring.designsystem.components.LightAndDarkPreview
import com.ku_stacks.ku_ring.designsystem.theme.Background
import com.ku_stacks.ku_ring.designsystem.theme.Gray300
import com.ku_stacks.ku_ring.designsystem.theme.KuringTheme
import com.ku_stacks.ku_ring.designsystem.theme.Pretendard
import com.ku_stacks.ku_ring.main.R

@Composable
internal fun SettingGroup(
    groupTitle: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Background)
    ) {
        SettingGroupTitle(
            title = groupTitle,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
        )
        content()
    }
}

@Composable
private fun SettingGroupTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight(400),
            color = Gray300,
            letterSpacing = 0.15.sp,
        ),
        modifier = modifier,
    )
}

@LightAndDarkPreview
@Composable
private fun SettingGroupPreview() {
    KuringTheme {
        SettingGroup(groupTitle = "정보") {
            SettingItem(
                iconId = R.drawable.ic_rocket,
                title = "앱 버전",
                onClick = {},
                content = {},
            )
            SettingItem(
                iconId = R.drawable.ic_star,
                title = "새로운 내용",
                onClick = {},
                content = {},
            )
        }
    }
}