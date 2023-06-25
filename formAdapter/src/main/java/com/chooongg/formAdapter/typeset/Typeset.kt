package com.chooongg.formAdapter.typeset

import android.view.ViewGroup
import androidx.annotation.GravityInt
import androidx.annotation.Px
import com.chooongg.formAdapter.FormManager
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.boundary.FormPaddingInfo
import com.chooongg.formAdapter.item.BaseForm

/**
 * 排版样式
 * 必须实现 equals 和 hashCode 方法
 */
abstract class Typeset(val ems: Int = FormManager.emsSize) {

    @GravityInt
    open fun contentGravity(): Int = FormManager.contentGravity

    @GravityInt
    open fun multiColumnContentGravity(): Int = FormManager.multiColumnContentGravity

    @Px
    open fun contentWidth(): Int = ViewGroup.LayoutParams.MATCH_PARENT

    abstract fun onCreateTypesetLayout(parent: ViewGroup, paddingInfo: FormPaddingInfo): ViewGroup?

    abstract fun onBindTypesetLayout(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Typeset) return false

        if (ems != other.ems) return false

        return true
    }

    override fun hashCode(): Int {
        return ems
    }
}