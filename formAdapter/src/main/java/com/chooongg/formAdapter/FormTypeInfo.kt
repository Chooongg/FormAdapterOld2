package com.chooongg.formAdapter

import com.chooongg.formAdapter.provider.BaseFormProvider
import com.chooongg.formAdapter.style.Style
import com.chooongg.formAdapter.typeset.Typeset

data class FormTypeInfo(
    val typeset: Typeset,
    val itemProvider: BaseFormProvider
)