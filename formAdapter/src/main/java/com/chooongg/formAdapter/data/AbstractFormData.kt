package com.chooongg.formAdapter.data

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormVisibilityMode

abstract class AbstractFormData {

    /**
     * 名称资源
     */
    @StringRes
    open var nameRes: Int? = null

    /**
     * 名称
     */
    open var name: CharSequence? = null

    /**
     * 可见模式
     */
    open var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 启用模式
     */
    open var enableMode: FormEnableMode = FormEnableMode.ONLY_EDIT

    /**
     * 真实的可见性
     */
    open fun isRealVisible(adapter: FormAdapter): Boolean {
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_EDIT -> adapter.isEditable
            FormVisibilityMode.ONLY_SHOW -> !adapter.isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 真实的可用性
     */
    open fun isRealEnable(adapter: FormAdapter): Boolean {
        return when (enableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ONLY_EDIT -> adapter.isEditable
            FormEnableMode.ONLY_SEE -> !adapter.isEditable
            FormEnableMode.NEVER -> false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractFormData) return false

        if (nameRes != other.nameRes) return false
        if (name != other.name) return false
        if (visibilityMode != other.visibilityMode) return false
        if (enableMode != other.enableMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nameRes?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + visibilityMode.hashCode()
        result = 31 * result + enableMode.hashCode()
        return result
    }
}