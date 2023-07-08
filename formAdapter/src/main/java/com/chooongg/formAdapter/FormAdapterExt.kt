package com.chooongg.formAdapter

import android.content.Context
import android.content.res.ColorStateList
import com.chooongg.formAdapter.data.LinkageForm
import com.chooongg.formAdapter.item.BaseOptionForm

typealias FormColorBlock = Context.() -> Int

typealias FormColorStateListBlock = Context.() -> ColorStateList

typealias FormLinkageBlock = (linkage: LinkageForm, field: String?, content: Any?) -> Unit

typealias FormOptionLoader<T> = suspend (BaseOptionForm<T>) -> List<T>?

typealias FormSliderFormatter = (Float) -> String

typealias FormPartNameFormatter = (context: Context, name: CharSequence?, index: Int) -> CharSequence