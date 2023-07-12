package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.InternalFormSpaceProvider

class InternalFormSpace : BaseForm(null, null, null) {
    override fun getItemProvider(adapter: FormAdapter) = InternalFormSpaceProvider
}