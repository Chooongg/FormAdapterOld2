package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.InternalFormGroupTitleProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset

class InternalFormGroupTitle internal constructor(
    name: CharSequence?
) : BaseForm(name, null) {

    override var isMustSingleColumn = true

    override var typeset: Typeset? = NoneTypeset

    override fun getItemProvider(adapter: FormAdapter) = InternalFormGroupTitleProvider

}