package com.ku_stacks.ku_ring.feedback.feedback

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ku_stacks.ku_ring.designsystem.theme.KuringTheme
import com.ku_stacks.ku_ring.feedback.R
import com.ku_stacks.ku_ring.feedback.feedback.compose.FeedbackScreen
import com.ku_stacks.ku_ring.ui_util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackActivity : AppCompatActivity() {

    private val viewModel by viewModels<FeedbackViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        observeViewModel()
    }

    private fun setupView() {

        setContent {
            KuringTheme {
                FeedbackScreen(
                    viewModel = viewModel,
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.quit.observe(this) {
            finish()
        }
        viewModel.toast.observe(this) {
            showToast(it)
        }
        viewModel.toastByResource.observe(this) {
            showToast(getString(it))
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_slide_left_enter, R.anim.anim_slide_left_exit)
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, FeedbackActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_slide_right_enter, R.anim.anim_stay_exit)
        }
    }
}
