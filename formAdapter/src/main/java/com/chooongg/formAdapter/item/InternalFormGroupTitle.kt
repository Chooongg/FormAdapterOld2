package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.InternalFormGroupTitleProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

class InternalFormGroupTitle internal constructor(
    @StringRes nameRes: Int?, name: CharSequence?
) : BaseForm(nameRes, name, null) {

    override var isMustSingleColumn = true

    var isDynamicPart = false

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(adapter: FormAdapter) = InternalFormGroupTitleProvider

}