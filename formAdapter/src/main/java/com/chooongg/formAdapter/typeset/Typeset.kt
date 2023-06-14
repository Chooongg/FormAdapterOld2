package com.chooongg.formAdapter.typeset

import android.view.ViewGroup
import androidx.annotation.GravityInt
import androidx.annotation.Px
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.enum.FormEmsMode
import com.chooongg.formAdapter.item.BaseForm

abstract class Typeset(val ems: Int, val emsMode: FormEmsMode) {

    @GravityInt
    open fun contentGravity(): Int = android.view.Gravity.NO_GRAVITY

    @Px
    open fun contentWidth(): Int = ViewGroup.LayoutParams.MATCH_PARENT

    abstract fun onCreateTypesetLayout(parent: ViewGroup): ViewGroup?

    abstract fun onBindTypesetLayout(holder: FormViewHolder, item: BaseForm)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Typeset) return false

        if (ems != other.ems) return false
        if (emsMode != other.emsMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = javaClass.hashCode()
        result = 31 * result + ems
        result = 31 * result + emsMode.hashCode()
        return result
    }
}