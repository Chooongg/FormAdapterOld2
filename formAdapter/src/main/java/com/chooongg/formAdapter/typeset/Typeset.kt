package com.chooongg.formAdapter.typeset

import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.GravityInt
import androidx.annotation.Px
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.enum.FormEmsMode
import com.chooongg.formAdapter.item.BaseForm

/**
 * 排版样式
 * 必须实现 equals 和 hashCode 方法
 */
abstract class Typeset(val ems: Int, val emsMode: FormEmsMode) {

    @GravityInt
    open fun contentGravity(): Int = android.view.Gravity.NO_GRAVITY

    @Px
    open fun contentWidth(): Int = ViewGroup.LayoutParams.MATCH_PARENT

    abstract fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo): ViewGroup?

    abstract fun onBindTypesetLayout(
        paddingInfo: FormPaddingInfo,
        holder: FormViewHolder,
        item: BaseForm
    )

    open fun setNameEms(textView: TextView) {
        when (emsMode) {
            FormEmsMode.NONE -> {
                textView.minWidth = 0
                textView.maxWidth = Int.MAX_VALUE
            }

            FormEmsMode.MIN -> {
                textView.minEms = ems
                textView.maxWidth = Int.MAX_VALUE
            }

            FormEmsMode.MAX -> {
                textView.minWidth = 0
                textView.maxEms = ems
            }

            FormEmsMode.FIXED -> {
                textView.setEms(ems)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Typeset) return false

        if (ems != other.ems) return false
        if (emsMode != other.emsMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ems
        result = 31 * result + emsMode.hashCode()
        return result
    }
}