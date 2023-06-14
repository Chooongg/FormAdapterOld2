package com.chooongg.formAdapter.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormHelper
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.item.BaseForm

/**
 * 继承此类的子类请使用 object 修饰
 */
abstract class BaseFormProvider {

    abstract fun onCreateItemView(parent: ViewGroup): View

    abstract fun onBindItemView(helper: FormHelper, holder: FormViewHolder, item: BaseForm)

    open fun onBindItemView(
        helper: FormHelper, holder: FormViewHolder, item: BaseForm, payloads: List<Any>
    ) = onBindItemView(helper, holder, item)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}