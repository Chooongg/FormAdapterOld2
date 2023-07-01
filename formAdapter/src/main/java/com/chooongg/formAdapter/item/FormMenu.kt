package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.provider.FormMenuProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

class FormMenu(name: CharSequence?, field: String?) : BaseForm(name, field) {

    companion object {
        const val NO_SET = -2
    }

    var isGlobalPadding:Boolean = true

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