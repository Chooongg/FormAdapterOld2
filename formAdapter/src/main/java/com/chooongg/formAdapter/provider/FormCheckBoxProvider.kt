package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormCheckBox
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.SpannableStyle
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.style
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.checkbox.MaterialCheckBox

object FormCheckBoxProvider : BaseFormProvider() {
    override fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = RecyclerView(parent.context).apply {
        id = R.id.formInternalContent
        isNestedScrollingEnabled = false
        overScrollMode = View.OVER_SCROLL_NEVER
        layoutManager = FlexboxLayoutManager(context)
        adapter = ChildAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemCheckBox = item as? FormCheckBox
        with(holder.getView<RecyclerView>(R.id.formInternalContent)) {
            val childAdapter = adapter as ChildAdapter
            childAdapter.partAdapter = partAdapter
            childAdapter.itemCheckBox = itemCheckBox
            childAdapter.notifyDataSetChanged()
            val flexboxLayoutManager = layoutManager as FlexboxLayoutManager
            val gravity = typeset.getContentGravity(partAdapter, item)
            flexboxLayoutManager.justifyContent = when {
                gravity and Gravity.END == Gravity.END -> JustifyContent.FLEX_END
                gravity and Gravity.CENTER == Gravity.CENTER -> JustifyContent.CENTER
                else -> JustifyContent.FLEX_START
            }
        }
    }

    private class ChildAdapter : RecyclerView.Adapter<FormViewHolder>() {

        lateinit var partAdapter: FormPartAdapter

        var itemCheckBox: FormCheckBox? = null
            set(value) {
                field = value
                options = itemCheckBox?.options
                selected = itemCheckBox?.selected
            }

        private var options: List<BaseOption>? = null

        private var selected: List<BaseOption>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
            return FormViewHolder(MaterialCheckBox(parent.context).apply {
                id = R.id.formInternalContentChild
                minWidth = 0
                minHeight = 0
                minimumWidth = 0
                minimumHeight = 0
            })
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
            val item = options!![position]
            with(holder.itemView as MaterialCheckBox) {
                setOnCheckedChangeListener(null)
                isEnabled = itemCheckBox?.isRealEnable(partAdapter.formAdapter) ?: false
                isChecked = itemCheckBox?.selected?.contains(item) ?: false
                val span = SpannableStyle(item.getName())
                val secondaryName = item.getSecondaryName()
                if (secondaryName != null) {
                    span.plus(" ")
                    span.plus(secondaryName.style {
                        setTextSizeRelative(0.8f)
                        setForegroundColor(attrColor(com.google.android.material.R.attr.colorOutline))
                    })
                }
                text = span.toSpannableString()
                if (itemCheckBox != null) {
                    setOnCheckedChangeListener { _, isChecked ->
                        partAdapter.formAdapter.clearFocus()
                        itemCheckBox!!.checkedOption(item, isChecked)
                        notifyLinkage(
                            partAdapter,
                            itemCheckBox!!,
                            itemCheckBox!!.field,
                            itemCheckBox!!.content
                        )
                    }
                }
                setPaddingRelative(
                    0,
                    partAdapter.style.paddingInfo.verticalLocal,
                    partAdapter.style.paddingInfo.horizontalLocal,
                    partAdapter.style.paddingInfo.verticalLocal
                )
            }
        }

        override fun getItemCount() = options?.size ?: 0
    }
}