package com.chooongg.formAdapter

import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.style.Style
import com.chooongg.formAdapter.typeset.Typeset

internal class FormItemType(
    val style: Class<Style>,
    val typeset: Typeset,
    val item: Class<BaseForm>
) {
}