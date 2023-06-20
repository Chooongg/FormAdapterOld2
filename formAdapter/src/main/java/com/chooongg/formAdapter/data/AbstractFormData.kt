package com.chooongg.formAdapter.data

import androidx.annotation.DrawableRes
import androidx.annotation.Px
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormVisibilityMode

abstract class AbstractFormData(
    /**
     * 名称
     */
    var name: CharSequence?
) {

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
            FormVisibilityMode.ONLY_SEE -> !adapter.isEditable
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
}