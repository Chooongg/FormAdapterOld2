package com.chooongg.formAdapter.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.provider.FormSelectorProvider
import com.chooongg.formAdapter.provider.FormTextProvider
import com.chooongg.utils.ext.style

fun FormCreator.addSelector(
    name: CharSequence?, field: String? = null, block: (FormSelector.() -> Unit)? = null
) = add(FormSelector(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addSelector(
    @StringRes nameRes: Int?, field: String? = null, block: (FormSelector.() -> Unit)? = null
) = add(FormSelector(nameRes, null, field).apply { block?.invoke(this) })

open class FormSelector(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseOptionForm<BaseOption>(nameRes, name, field) {

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