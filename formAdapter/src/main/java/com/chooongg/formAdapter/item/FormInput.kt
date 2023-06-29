package com.chooongg.formAdapter.item

import android.content.Context
import android.content.res.ColorStateList
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.provider.FormInputProvider
import com.chooongg.formAdapter.provider.FormTextProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.BoxBackgroundMode

class FormInput(name: CharSequence?, field: String?) : BaseForm(name, field) {

    var backgroundColor: (Context.() -> ColorStateList)? = null

    @BoxBackgroundMode
    var backgroundMode: Int = TextInputLayout.BOX_BACKGROUND_NONE

    var maxLines: Int = Int.MAX_VALUE

    var minLines: Int = 0

    var prefixText: CharSequence? = null

    var suffixText: CharSequence? = null

    var placeholderText: CharSequence? = null

    override fun getItemProvider(adapter: FormAdapter) =
        if (adapter.isEditable) FormInputProvider else FormTextProvider
}