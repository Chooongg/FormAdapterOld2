package com.chooongg.formAdapter

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.RecyclerView

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var paddingVertical: Int = 0
    private var paddingHorizontal: Int = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FormView)
        paddingVertical =
            a.getDimensionPixelSize(R.styleable.FormView_formPaddingVertical, dp2px(16f))
        paddingHorizontal =
            a.getDimensionPixelSize(R.styleable.FormView_formPaddingHorizontal, dp2px(16f))
        a.recycle()
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_DRAGGING && focusedChild != null) {
                    context.getSystemService<InputMethodManager>()
                        ?.hideSoftInputFromWindow(focusedChild.windowToken, 0)
                    focusedChild.clearFocus()
                }
            }
        })
    }

    fun setPadding(paddingVertical: Int, paddingHorizontal: Int) {
        this.paddingVertical = paddingVertical
        this.paddingHorizontal = paddingHorizontal
        (layoutManager as? FormLayoutManager)?.also {
            it.setPadding(paddingVertical, paddingHorizontal)
        }
    }

    override fun setLayoutManager(layout: RecyclerView.LayoutManager?) {
        if (layout is FormLayoutManager) {
            layout.setPadding(paddingVertical, paddingHorizontal)
        }
        super.setLayoutManager(layout)
    }

    fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}