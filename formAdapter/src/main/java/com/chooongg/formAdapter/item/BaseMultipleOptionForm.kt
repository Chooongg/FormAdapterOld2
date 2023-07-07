package com.chooongg.formAdapter.item

import androidx.annotation.StringRes

abstract class BaseMultipleOptionForm<T>(
    @StringRes nameRes: Int?, name: CharSequence?, field: String?
) : BaseOptionForm<T>(nameRes, name, field)