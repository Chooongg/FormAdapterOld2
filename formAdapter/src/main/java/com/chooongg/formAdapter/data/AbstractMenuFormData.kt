package com.chooongg.formAdapter.data

import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormVisibilityMode

abstract class AbstractMenuFormData : AbstractFormData() {

    /**
     * 菜单文本资源
     */
    @StringRes
    open var menuTextRes: Int? = null

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractMenuFormData) return false
        if (!super.equals(other)) return false

        if (menuTextRes != other.menuTextRes) return false
        if (menuText != other.menuText) return false
        if (menuIconRes != other.menuIconRes) return false
        if (menuVisibilityMode != other.menuVisibilityMode) return false
        if (menuEnableMode != other.menuEnableMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (menuTextRes?.hashCode() ?: 0)
        result = 31 * result + (menuText?.hashCode() ?: 0)
        result = 31 * result + (menuIconRes?.hashCode() ?: 0)
        result = 31 * result + menuVisibilityMode.hashCode()
        result = 31 * result + menuEnableMode.hashCode()
        return result
    }
}