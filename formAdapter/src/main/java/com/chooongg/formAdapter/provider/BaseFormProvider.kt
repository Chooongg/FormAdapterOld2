package com.chooongg.formAdapter.provider

import android.view.View
import android.view.ViewGroup
import androidx.annotation.GravityInt
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
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

    /**
     * 获取内容的对齐方式
     */
    @GravityInt
    open fun getContentGravity(adapter: FormPartAdapter, typeset: Typeset, item: BaseForm): Int {
        return if (item.isMustSingleColumn || adapter.formAdapter.normalColumnCount <= 1) {
            typeset.contentGravity()
        } else typeset.multiColumnContentGravity()
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