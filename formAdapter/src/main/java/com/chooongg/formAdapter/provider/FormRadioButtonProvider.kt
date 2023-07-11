package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormRadioButton
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.SpannableStyle
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.style
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlin.math.min

object FormRadioButtonProvider : BaseFormProvider() {

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
        val itemRadioButton = item as? FormRadioButton
        with(holder.getView<RecyclerView>(R.id.formInternalContent)) {
            val childAdapter = adapter as ChildAdapter
            childAdapter.partAdapter = partAdapter
            childAdapter.itemRadioButton = itemRadioButton
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

        var itemRadioButton: FormRadioButton? = null
            set(value) {
                field = value
                options = itemRadioButton?.options
            }

        private var options: List<BaseOption>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
            return FormViewHolder(MaterialRadioButton(parent.context).apply {
                id = R.id.formInternalContentChild
                minWidth = 0
                minHeight = 0
                minimumWidth = 0
                minimumHeight = 0
                background = RippleDrawable(
                    ColorStateList.valueOf(attrColor(android.R.attr.colorControlHighlight)),
                    null,
                    null
                ).apply {
                    radius = textSize.toInt() / 2 + min(
                        partAdapter.style.paddingInfo.verticalLocal,
                        partAdapter.style.paddingInfo.horizontalLocal
                    )
                }
            })
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
            val item = options!![position]
            with(holder.itemView as MaterialRadioButton) {
                setOnCheckedChangeListener(null)
                isEnabled = itemRadioButton?.isRealEnable(partAdapter.formAdapter) ?: false
                isChecked = itemRadioButton?.content == item
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
                if (itemRadioButton != null) {
                    setOnCheckedChangeListener { _, checked ->
                        if (!checked) return@setOnCheckedChangeListener
                        partAdapter.formAdapter.clearFocus()
                        val selectedPosition =
                            options?.indexOfFirst { itemRadioButton!!.content == it } ?: -1
                        FormRadioButtonProvider.changeContentAndNotifyLinkage(
                            partAdapter, itemRadioButton!!, item
                        )
                        if (selectedPosition != -1 && selectedPosition != position) {
                            post { notifyItemChanged(selectedPosition, "uncheck") }
                        }
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

        override fun onBindViewHolder(
            holder: FormViewHolder,
            position: Int,
            payloads: MutableList<Any>
        ) {
            if (payloads.contains("uncheck")) {
                (holder.itemView as MaterialRadioButton).isChecked = false
                return
            }
            super.onBindViewHolder(holder, position, payloads)
        }

        override fun getItemCount() = options?.size ?: 0
    }
}