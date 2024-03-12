package com.ku_stacks.ku_ring.main.notice.department.fragment_subscribe

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ku_stacks.ku_ring.domain.Department
import com.ku_stacks.ku_ring.main.R
import com.ku_stacks.ku_ring.main.databinding.FragmentDepartmentSubscribeBottomSheetBinding
import com.ku_stacks.ku_ring.main.notice.department.fragment_subscribe.listener.DepartmentEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Deprecated("학과별 공지 composable 업데이트 후 삭제")
@AndroidEntryPoint
class DepartmentSubscribeBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDepartmentSubscribeBottomSheetBinding

    private val mainViewModel: DepartmentSubscribeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
            // TODO : 최대높이 동작하도록 수정
            behavior.peekHeight = resources.displayMetrics.heightPixels
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDepartmentSubscribeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
        initView()
        initRecyclerView()
        observeViewModel()
    }

    private fun bindViewModel() {
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initView() {
        initRecyclerView()
        binding.confirmButton.setOnClickListener {
            dismiss()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = DepartmentListAdapter(
                departmentEventListener = getDepartmentEventListener()
            )
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getDepartmentEventListener(): DepartmentEventListener {
        return object : DepartmentEventListener {
            override fun onClickDepartment(department: Department) {
                mainViewModel.onSubscribeClick(department)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.departmentStateFlow.collect { state ->
                    when (state) {
                        is DepartmentStateData.Success -> {
                            (binding.recyclerView.adapter as? DepartmentListAdapter)?.apply {
                                submitList(state.list)
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }

    }

    companion object {
        fun newInstance(): DepartmentSubscribeBottomSheet {
            return DepartmentSubscribeBottomSheet()
        }
    }
}
