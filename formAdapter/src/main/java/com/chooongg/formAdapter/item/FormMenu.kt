package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormMenuProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

fun FormCreator.addMenu(
    name: CharSequence?, field: String? = null, block: (FormMenu.() -> Unit)? = null
) = add(FormMenu(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addMenu(
    @StringRes nameRes: Int?, field: String? = null, block: (FormMenu.() -> Unit)? = null
) = add(FormMenu(nameRes, null, field).apply { block?.invoke(this) })

class FormMenu(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

    companion object {
        const val NO_SET = -2
    }

    var isGlobalPadding: Boolean = true

    var iconRes: Int? = null

    var iconSize: Int? = null

    var iconTint: FormColorStateListBlock? = null

    var isShowMore: Boolean = true

    var badgeNumber: Int? = null
        set(value) {
            field = value
            if (value != null) badgeText = null
        }

    var badgeMaxNumber: Int = NO_SET

    var badgeText: String? = null
        set(value) {
            field = value
            if (value != null) badgeNumber = null
        }


    override var typeset: Typeset? = NoneTypeset

    override var isMustSingleColumn: Boolean = true

    override fun getItemProvider(adapter: FormAdapter) = FormMenuProvider
}