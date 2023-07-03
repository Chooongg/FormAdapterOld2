package com.chooongg.formAdapter

import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.item.FormButton
import com.chooongg.formAdapter.item.FormDivider
import com.chooongg.formAdapter.item.FormInput
import com.chooongg.formAdapter.item.FormLabel
import com.chooongg.formAdapter.item.FormMenu
import com.chooongg.formAdapter.item.FormSelector
import com.chooongg.formAdapter.item.FormSwitch
import com.chooongg.formAdapter.item.FormText
import com.chooongg.formAdapter.item.FormTip

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

fun FormCreator.addTip(
    name: CharSequence?, field: String? = null, block: (FormTip.() -> Unit)? = null
) = add(FormTip(name, field).apply { block?.invoke(this) })

fun FormCreator.addLabel(
    name: CharSequence?, field: String? = null, block: (FormLabel.() -> Unit)? = null
) = add(FormLabel(name, field).apply { block?.invoke(this) })

fun FormCreator.addMenu(
    name: CharSequence?, field: String? = null, block: (FormMenu.() -> Unit)? = null
) = add(FormMenu(name, field).apply { block?.invoke(this) })

fun FormCreator.addSwitch(
    name: CharSequence?, field: String? = null, block: (FormSwitch.() -> Unit)? = null
) = add(FormSwitch(name, field).apply { block?.invoke(this) })