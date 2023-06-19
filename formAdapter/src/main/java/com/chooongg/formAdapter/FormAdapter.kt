package com.chooongg.formAdapter

import com.chooongg.formAdapter.data.PartData
import com.chooongg.formAdapter.style.NoneStyle
import com.chooongg.formAdapter.style.Style

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
            create(block)
            plusPart(this)
        }

    fun plusPart(index: Int, style: Style = NoneStyle, block: PartData.() -> Unit) =
        FormPartAdapter(this, style).apply {
            create(block)
            plusPart(index, this)
        }

    fun clearFocus() {
        _recyclerView?.get()?.focusedChild?.clearFocus()
    }
}