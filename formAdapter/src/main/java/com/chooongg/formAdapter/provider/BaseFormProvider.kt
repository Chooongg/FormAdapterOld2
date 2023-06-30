package com.chooongg.formAdapter.provider

import android.view.View
import android.view.ViewGroup
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.data.LinkageForm
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset

/**
 * 继承此类的子类请使用 object 修饰
 */
abstract class BaseFormProvider {

    abstract fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ): View

    abstract fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    )

    open fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm,
        payloads: List<Any>
    ) = onBindItemView(adapter, typeset, holder, item)

    open fun onItemRecycler(holder: FormViewHolder) = Unit

    protected fun changeContent(adapter: FormPartAdapter, item: BaseForm, content: Any?) {
        if (item.content != content) {
            item.content = content
            item.linkageBlock?.invoke(LinkageForm(adapter), item.field, item.content)
        }
    }

    protected fun changeEx(
        adapter: FormPartAdapter,
        item: BaseForm,
        field: String,
        content: Any?
    ) {
        if (item.getExtensionContent(field) != content) {
            item.putExtensionContent(field, content)
            item.linkageBlock?.invoke(LinkageForm(adapter), field, content)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}