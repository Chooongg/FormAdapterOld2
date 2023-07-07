package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.FormTextProvider

/**
 * 单行表单
 * max:10
 */
class SingleLineForm internal constructor() : BaseForm(null, null, null) {

    internal var items = mutableListOf<BaseForm>()

    override fun getItemProvider(adapter: FormAdapter) = FormTextProvider
}