package com.chooongg.formAdapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.utils.ext.getActivity
import com.chooongg.utils.ext.hideIME
import com.chooongg.utils.ext.resDimensionPixelSize

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var formPaddingStart: Int = 0
    private var formPaddingEnd: Int = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FormView)
        formPaddingStart =
            a.getDimensionPixelSize(
                R.styleable.FormView_formPaddingStart,
                resDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize)
            )
        formPaddingEnd =
            a.getDimensionPixelSize(
                R.styleable.FormView_formPaddingEnd,
                resDimensionPixelSize(R.dimen.formHorizontalGlobalMarginSize)
            )
        a.recycle()
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == SCROLL_STATE_DRAGGING && focusedChild != null) {
                    focusedChild.clearFocus()
                    context.getActivity()?.hideIME()
                }
            }
        })
        itemAnimator = FormItemAnimator()
    }

    fun setPadding(paddingVertical: Int, paddingHorizontal: Int) {
        this.formPaddingStart = paddingVertical
        this.formPaddingEnd = paddingHorizontal
        (layoutManager as? FormLayoutManager)?.also {
            it.setPadding(paddingVertical, paddingHorizontal)
        }
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout is FormLayoutManager) {
            layout.setPadding(formPaddingStart, formPaddingEnd)
        }
        super.setLayoutManager(layout)
    }
}