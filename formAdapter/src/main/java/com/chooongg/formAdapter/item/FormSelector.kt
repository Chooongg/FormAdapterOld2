package com.chooongg.formAdapter.item

import android.content.Context
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.FormSelectorProvider
import com.chooongg.formAdapter.provider.FormTextProvider
import com.chooongg.utils.ext.style

open class FormSelector(name: CharSequence?, field: String?) : BaseOptionForm<BaseOption>(name, field) {

    /**
     * 打开模式
     */
    var openMode: FormSelectorOpenMode = FormSelectorOpenMode.AUTO

    override fun hasOpenOperation() = true

    override fun getItemProvider(adapter: FormAdapter) =
        if (isRealEnable(adapter)) FormSelectorProvider else FormTextProvider

    override fun getContentText(context: Context): CharSequence? {
        if (content == null) return null
        val option = content as? BaseOption ?: return content?.toString()
        val text =
            option.getName().style {} + " ".style {} + (option.getSecondaryName() ?: "").style {
                setTextSizeRelative(0.8f)
            }
        return text.toSpannableString()
    }
}