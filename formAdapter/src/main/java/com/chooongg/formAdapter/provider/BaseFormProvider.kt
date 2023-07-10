package com.chooongg.formAdapter.provider

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.data.LinkageForm
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ClipboardUtils

/**
 * 继承此类的子类请使用 object 修饰
 */
abstract class BaseFormProvider {

    abstract fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ): View

    abstract fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    )

    open fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm,
        payloads: List<Any>
    ) = onBindItemView(partAdapter, typeset, holder, item)

    open fun onBindItemViewForeground(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ): Drawable? = null

    open fun isEnableItemClick(): Boolean = false

    open fun onBindItemViewClick(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        if (isEnableItemClick()) {
            holder.itemView.setOnClickListener {

            }
        } else holder.itemView.setOnClickListener(null)
    }

    open fun onBindItemViewLongClick(
        partAdapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.itemView.setOnLongClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menu.add(0, 0, 0, "复制名称")
            if (item.getContentText(partAdapter, holder) != null) {
                popupMenu.menu.add(0, 1, 1, "复制内容")
            }
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    0 -> ClipboardUtils.copyText(item.name)
                    1 -> ClipboardUtils.copyText(item.getContentText(partAdapter, holder))
                    else -> return@setOnMenuItemClickListener false
                }
                true
            }
            popupMenu.show()
            true
        }
    }

    open fun onViewAttachedToWindow(holder: FormViewHolder) = Unit

    open fun onViewDetachedFromWindow(holder: FormViewHolder) = Unit

    open fun onItemRecycler(holder: FormViewHolder) = Unit

    protected open fun changeContentAndNotifyLinkage(
        partAdapter: FormPartAdapter,
        item: BaseForm,
        content: Any?
    ) {
        if (item.content != content) {
            item.content = content
            notifyLinkage(partAdapter, item, item.field, content)
        }
    }

    protected fun changeExtensionAndNotifyLinkage(
        partAdapter: FormPartAdapter,
        item: BaseForm,
        field: String,
        content: Any?
    ) {
        if (item.getExtensionContent(field) != content) {
            item.putExtensionContent(field, content)
            notifyLinkage(partAdapter, item, field, content)
        }
    }

    protected fun notifyLinkage(
        partAdapter: FormPartAdapter,
        item: BaseForm,
        field: String?,
        content: Any?
    ) {
        item.linkageBlock?.invoke(LinkageForm(partAdapter), field, content)
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