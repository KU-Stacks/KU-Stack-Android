package com.ku_stacks.ku_ring.notice_storage.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ku_stacks.ku_ring.designsystem.components.KuringAlertDialog
import com.ku_stacks.ku_ring.designsystem.components.LightAndDarkPreview
import com.ku_stacks.ku_ring.designsystem.theme.KuringTheme
import com.ku_stacks.ku_ring.designsystem.theme.Warning
import com.ku_stacks.ku_ring.notice_storage.R

@Composable
internal fun DeleteSelectedNoticesAlertDialog(
    isDeletePopupVisible: Boolean,
    isDeleteAllNotices: Boolean,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isDeletePopupVisible,
        modifier = modifier,
    ) {
        val textId =
            if (isDeleteAllNotices) R.string.alert_dialog_delete_all_notices else R.string.alert_dialog_delete_notices
        KuringAlertDialog(
            text = stringResource(id = textId),
            onConfirm = onDelete,
            confirmText = stringResource(id = R.string.alert_dialog_delete_text),
            onCancel = onDismiss,
            cancelText = stringResource(id = R.string.alert_dialog_cancel_text),
            confirmTextColor = Warning,
        )
    }
}

@LightAndDarkPreview
@Composable
private fun DeleteSelectedNoticesAlertDialogPreview() {
    KuringTheme {
        DeleteSelectedNoticesAlertDialog(
            isDeletePopupVisible = true,
            isDeleteAllNotices = true,
            onDelete = { },
            onDismiss = { },
        )
    }
}