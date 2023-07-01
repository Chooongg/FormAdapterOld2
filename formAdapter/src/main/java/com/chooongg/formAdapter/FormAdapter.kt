package com.chooongg.formAdapter

import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.chooongg.formAdapter.data.PartData
import com.chooongg.formAdapter.style.NoneStyle
import com.chooongg.formAdapter.style.Style
import com.chooongg.utils.ext.getActivity
import com.chooongg.utils.ext.hideIME

class FormAdapter(isEditable: Boolean, block: (FormAdapter.() -> Unit)?) :
    FormConcatAdapter(isEditable) {

    init {
        block?.invoke(this)
    }

    fun createPart(vararg part: FormPartAdapter) {
        part.forEach { plusPart(it) }
    }

    fun plusPart(style: Style = NoneStyle, block: PartData.() -> Unit) =
        FormPartAdapter(this, style).apply {
            plusPart(this)
            create(block)
        }

    fun plusPart(index: Int, style: Style = NoneStyle, block: PartData.() -> Unit) =
        FormPartAdapter(this, style).apply {
            plusPart(index, this)
            create(block)
        }

    fun clearFocus() {
        _recyclerView.get()?.also {
            val focusedChild = it.focusedChild
            if (focusedChild != null) {
                focusedChild.clearFocus()
                it.context.getActivity()?.hideIME()
            }
        }
    }
}