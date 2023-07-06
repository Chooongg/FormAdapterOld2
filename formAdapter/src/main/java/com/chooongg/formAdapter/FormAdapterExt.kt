package com.chooongg.formAdapter

import android.content.Context
import android.content.res.ColorStateList
import com.chooongg.formAdapter.data.LinkageForm
import com.chooongg.formAdapter.item.BaseOptionForm
import com.chooongg.formAdapter.option.OptionResult

typealias FormColorBlock = Context.() -> Int

typealias FormColorStateListBlock = Context.() -> ColorStateList

typealias FormLinkageBlock = (linkage: LinkageForm, field: String?, content: Any?) -> Unit

typealias FormOptionLoader<T> = suspend (BaseOptionForm<T>) -> OptionResult<T>