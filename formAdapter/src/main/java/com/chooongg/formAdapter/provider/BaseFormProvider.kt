package com.chooongg.formAdapter.provider

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.data.LinkageForm
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ClipboardUtils
import com.chooongg.utils.ext.attrColor
import com.google.android.material.shape.MaterialShapeDrawable

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

    open fun onBindItemViewForeground(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ): Drawable? = RippleDrawable(
        ColorStateList.valueOf(
            holder.itemView.attrColor(androidx.appcompat.R.attr.colorControlHighlight)
        ),
        null,
        holder.itemView.background ?: MaterialShapeDrawable(
            adapter.style.getShapeAppearanceModel(holder, item)
        )
    )

    open fun onBindItemViewClick(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.itemView.setOnClickListener {

        }
    }

    open fun onBindItemViewLongClick(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        holder.itemView.setOnLongClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menu.add(0, 0, 0, "复制名称")
            if (item.getContentText() != null) {
                popupMenu.menu.add(0, 1, 1, "复制内容")
            }
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    0 -> ClipboardUtils.copyText(item.name)
                    1 -> ClipboardUtils.copyText(item.getContentText())
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

    protected fun changeContentAndNotifyLinkage(
        adapter: FormPartAdapter,
        item: BaseForm,
        content: Any?
    ) {
        if (item.content != content) {
            item.content = content
            item.linkageBlock?.invoke(LinkageForm(adapter), item.field, item.content)
        }
    }

    protected fun changeExtensionAndNotifyLinkage(
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