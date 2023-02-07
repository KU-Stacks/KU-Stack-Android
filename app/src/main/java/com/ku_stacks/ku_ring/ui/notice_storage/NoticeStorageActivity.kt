package com.ku_stacks.ku_ring.ui.notice_storage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ku_stacks.ku_ring.R
import com.ku_stacks.ku_ring.data.mapper.concatSubjectAndTag
import com.ku_stacks.ku_ring.databinding.ActivityNoticeStorageBinding
import com.ku_stacks.ku_ring.ui.notice_storage.right_swipe.RightSwipeHandler
import com.ku_stacks.ku_ring.ui.notice_storage.ui_model.SavedNoticeUiModel
import com.ku_stacks.ku_ring.ui.notice_webview.NoticeWebActivity
import com.ku_stacks.ku_ring.util.UrlGenerator
import com.ku_stacks.ku_ring.util.makeDialog
import com.yeonkyu.HoldableSwipeHelper.SwipeButtonAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class NoticeStorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeStorageBinding
    private val viewModel by viewModels<NoticeStorageViewModel>()
    private lateinit var storageAdapter: NoticeStorageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()
        setView()
        setListAdapter()
        collectData()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_storage)
        binding.lifecycleOwner = this
    }

    private fun setView() {
        binding.backImage.setOnClickListener {
            finish()
        }
        binding.clearNoticesImage.setOnClickListener {
            makeDialog(title = "모두 삭제할까요?").setOnConfirmClickListener { viewModel.clearNotices() }
        }
    }

    private fun setListAdapter() {
        storageAdapter = NoticeStorageAdapter {
            startNoticeActivity(it)
        }
        binding.notificationStorageRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@NoticeStorageActivity)
            adapter = storageAdapter
        }

        val removeDrawable =
            AppCompatResources.getDrawable(this, R.drawable.ic_bookmark_remove)!!
        RightSwipeHandler.Builder(this)
            .setSwipeButtonAction(object : SwipeButtonAction {
                override fun onClickFirstButton(absoluteAdapterPosition: Int) {
                    val notice = storageAdapter.currentList[absoluteAdapterPosition]
                    viewModel.deleteNotice(notice.articleId)
                }
            })
            .setBackgroundColor(getColor(R.color.kus_green))
            .setFirstItemDrawable(removeDrawable)
            .setDismissOnClickFirstItem(true)
            .setOnRecyclerView(binding.notificationStorageRecyclerview)
            .build()
    }

    private fun collectData() {
        lifecycleScope.launchWhenResumed {
            viewModel.savedNotices.collectLatest {
                binding.notificationStorageAlert.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.GONE
                storageAdapter.submitList(it)
            }
        }
    }

    private fun startNoticeActivity(model: SavedNoticeUiModel) {
        val (articleId, category, baseUrl, postedDate, subject, tag) = model
        val url = UrlGenerator.generateNoticeUrl(articleId, category, baseUrl)
        Timber.e("URL: $url, category: $category")

        val intent = Intent(this, NoticeWebActivity::class.java).apply {
            putExtra(NoticeWebActivity.NOTICE_URL, url)
            putExtra(NoticeWebActivity.NOTICE_ARTICLE_ID, articleId)
            putExtra(NoticeWebActivity.NOTICE_CATEGORY, category)
            putExtra(NoticeWebActivity.NOTICE_POSTED_DATE, postedDate)
            putExtra(NoticeWebActivity.NOTICE_SUBJECT, concatSubjectAndTag(subject, tag))
        }
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_right_enter, R.anim.anim_stay_exit)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_slide_left_enter, R.anim.anim_slide_left_exit)
    }
}