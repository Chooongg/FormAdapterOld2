package com.chooongg.formAdapter

import android.content.Context
import android.content.res.ColorStateList
import com.chooongg.formAdapter.data.LinkageForm

typealias FormColorBlock = Context.() -> Int

typealias FormColorStateListBlock = Context.() -> ColorStateList

typealias FormLinkageBlock = (linkage: LinkageForm, field: String?, content: Any?) -> Unit