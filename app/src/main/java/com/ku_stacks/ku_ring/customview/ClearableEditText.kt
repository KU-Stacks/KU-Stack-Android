package com.ku_stacks.ku_ring.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.addTextChangedListener
import com.ku_stacks.ku_ring.R

class ClearableEditText : AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr)

    private lateinit var clearDrawable: Drawable

    init {
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        val rawDrawable = ContextCompat.getDrawable(context, R.drawable.ic_close)!!
        clearDrawable = DrawableCompat.wrap(rawDrawable)

        DrawableCompat.setTintList(clearDrawable, hintTextColors)
        clearDrawable.setBounds(0, 0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)

        addTextChangedListener { editable ->
            val isVisible = editable?.isNotEmpty() ?: false
            showOrHideClearDrawable(isVisible)
        }

        super.setOnTouchListener(this)
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        motionEvent?.x?.let { x ->
            if (clearDrawable.isVisible && x > width - paddingRight - clearDrawable.intrinsicWidth) {
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    error = null
                    text = null
                }
                return true
            }
        }

        return false
    }

    private fun showOrHideClearDrawable(visible: Boolean) {
        setCompoundDrawables(compoundDrawables[0], null, if (visible) clearDrawable else null, null)
    }

}