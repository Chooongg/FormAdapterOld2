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
            plusPart(this)
            create(block)
            val lastPart = adapters.size - 2
            if (lastPart >= 0) partAdapters[lastPart].update()
        }

    fun plusPart(index: Int, style: Style = NoneStyle, block: PartData.() -> Unit) =
        FormPartAdapter(this, style).apply {
            plusPart(index, this)
            create(block)
            if (index - 1 >= 0) partAdapters[index - 1].update()
            if (index + 1 < adapters.size) partAdapters[index + 1].update()
        }

    fun clearFocus() {
        _recyclerView?.get()?.focusedChild?.clearFocus()
    }
}