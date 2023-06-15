package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.FormText

fun GroupData.addText(name: CharSequence?, field: String?, block: FormText.() -> Unit) =
    add(FormText(name, field).apply(block))