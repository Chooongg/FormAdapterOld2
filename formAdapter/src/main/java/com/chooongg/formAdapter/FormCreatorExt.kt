package com.chooongg.formAdapter

import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.item.FormButton
import com.chooongg.formAdapter.item.FormDivider
import com.chooongg.formAdapter.item.FormInput
import com.chooongg.formAdapter.item.FormSelector
import com.chooongg.formAdapter.item.FormText

fun FormCreator.addButton(
    name: CharSequence?, field: String? = null, block: (FormButton.() -> Unit)? = null
) = add(FormButton(name, field).apply { block?.invoke(this) })

fun FormCreator.addDivider(
    field: String? = null, block: (FormDivider.() -> Unit)? = null
) = add(FormDivider(field).apply { block?.invoke(this) })

fun FormCreator.addInput(
    name: CharSequence?, field: String? = null, block: (FormInput.() -> Unit)? = null
) = add(FormInput(name, field).apply { block?.invoke(this) })

fun FormCreator.addSelector(
    name: CharSequence?, field: String? = null, block: (FormSelector.() -> Unit)? = null
) = add(FormSelector(name, field).apply { block?.invoke(this) })

fun FormCreator.addText(
    name: CharSequence?, field: String? = null, block: (FormText.() -> Unit)? = null
) = add(FormText(name, field).apply { block?.invoke(this) })
