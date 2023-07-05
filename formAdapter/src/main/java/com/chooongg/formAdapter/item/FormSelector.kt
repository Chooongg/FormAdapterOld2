package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.FormSelectorProvider
import com.chooongg.formAdapter.provider.FormTextProvider

open class FormSelector(name: CharSequence?, field: String?) : BaseOptionForm(name, field) {

    /**
     * 打开模式
     */
    var openMode: FormSelectorOpenMode = FormSelectorOpenMode.AUTO

    override fun hasOpenOperation() = true

    override fun getItemProvider(adapter: FormAdapter) =
        if (isRealEnable(adapter)) FormSelectorProvider else FormTextProvider

    override fun getContentText(): CharSequence? {
        return (content as? BaseOption)?.getName() ?: content?.toString()
    }
}