package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.FormInputProvider
import com.chooongg.formAdapter.provider.FormTextProvider
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.BoxBackgroundMode

class FormInput(name: CharSequence?, field: String?) : BaseForm(name, field) {

    var prefixText: CharSequence? = null

    var suffixText: CharSequence? = null

    var placeholderText: CharSequence? = null

    var isShowCounter: Boolean? = null

    var maxLength: Int = Int.MAX_VALUE

    var maxLines: Int = Int.MAX_VALUE

    var minLines: Int = 0

    override fun getItemProvider(adapter: FormAdapter) =
        if (isRealEnable(adapter)) FormInputProvider else FormTextProvider
}