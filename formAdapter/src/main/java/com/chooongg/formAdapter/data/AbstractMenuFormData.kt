package com.chooongg.formAdapter.data

import androidx.annotation.DrawableRes
import androidx.annotation.Px
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormVisibilityMode

abstract class AbstractMenuFormData(name: CharSequence?) : AbstractFormData(name) {

    /**
     * 菜单文本
     */
    open var menuText: CharSequence? = null

    /**
     * 菜单图标
     */
    @DrawableRes
    open var menuIconRes: Int? = null

    /**
     * 菜单图标大小
     */
    @Px
    open var menuIconSize: Int? = null

    /**
     * 菜单可见模式
     */
    open var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 菜单启用模式
     */
    open var menuEnableMode: FormEnableMode = FormEnableMode.ONLY_EDIT

    /**
     * 真实的菜单可见性
     */
    open fun isRealMenuVisible(isEditable: Boolean): Boolean {
        if (menuText == null && menuIconRes == null) return false
        return when (menuVisibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_EDIT -> isEditable
            FormVisibilityMode.ONLY_SHOW -> !isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 真实的菜单可用性
     */
    open fun isRealMenuEnable(adapter: FormAdapter): Boolean {
        return when (menuEnableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ONLY_EDIT -> adapter.isEditable
            FormEnableMode.ONLY_SEE -> !adapter.isEditable
            FormEnableMode.NEVER -> false
        }
    }
}