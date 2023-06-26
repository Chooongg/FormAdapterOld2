package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.FormSelector
import com.chooongg.formAdapter.item.FormText

fun FormCreator.addText(
    name: CharSequence?, field: String?, block: (FormText.() -> Unit)? = null
) = add(FormText(name, field).apply { block?.invoke(this) })

fun FormCreator.addSelector(
    name: CharSequence?, field: String?, block: (FormSelector.() -> Unit)? = null
) = add(FormSelector(name, field).apply { block?.invoke(this) })