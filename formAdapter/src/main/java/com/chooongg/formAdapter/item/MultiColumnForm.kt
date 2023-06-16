package com.chooongg.formAdapter.item

import androidx.annotation.IntRange
import com.chooongg.formAdapter.FormHelper
import com.chooongg.formAdapter.provider.FormTextProvider

class MultiColumnForm internal constructor() : BaseForm(null, null) {

    @IntRange(from = 1, to = 5)
    var maxColumn = 2

    internal var items = mutableListOf<BaseForm>()

    override fun getItemProvider(helper: FormHelper) = FormTextProvider
}