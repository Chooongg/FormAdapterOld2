package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.FormTextProvider

class SingleLineForm internal constructor() : BaseForm(null, null) {

    internal var items = mutableListOf<BaseForm>()

    override fun getItemProvider(adapter: FormAdapter) = FormTextProvider
}