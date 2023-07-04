package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormSelector
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.doOnClick
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.DeterminateDrawable

object FormSelectorProvider : BaseFormProvider() {

    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = MaterialButton(
        parent.context, null, com.google.android.material.R.attr.borderlessButtonStyle
    ).apply {
        id = R.id.formInternalContent
        minimumHeight = 0
        minimumWidth = 0
        minHeight = 0
        minWidth = 0
        insetBottom = 0
        insetTop = 0
        iconSize = resources.getDimensionPixelSize(R.dimen.formIconSize)
        iconTint = ColorStateList.valueOf(hintTextColors.defaultColor)
        iconGravity = MaterialButton.ICON_GRAVITY_END
        setIconResource(R.drawable.ic_form_arrow_down)
        setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setPadding(
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal,
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal
        )
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        )
    }

    @SuppressLint("ResourceType")
    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemSelector = item as? FormSelector
        val drawable = DeterminateDrawable.createCircularDrawable(
            holder.itemView.context,
            CircularProgressIndicatorSpec(holder.itemView.context, null)
        )
        with(holder.getView<MaterialButton>(R.id.formInternalContent)) {
            isEnabled = item.isRealMenuEnable(adapter.formAdapter)
            text = item.getContentText()
            hint = item.hint ?: resources.getString(R.string.fromDefaultHintNone)
            gravity = typeset.getContentGravity(adapter, item)
            updateLayoutParams<ViewGroup.LayoutParams> {
                width = typeset.contentWidth()
            }
            doOnClick {
                onClickButton(adapter, holder, item)

            }
//            icon = drawable
//            drawable.start()
        }
    }

    private fun onClickButton(adapter: FormPartAdapter, holder: FormViewHolder, item: BaseForm) {
        if (item !is FormSelector) return
        if (!item.options.isNullOrEmpty()) {
            show(adapter, holder, item)
        }
    }

    private fun show(adapter: FormPartAdapter, holder: FormViewHolder, item: FormSelector) {
        adapter.formAdapter.clearFocus()
        when (item.openMode) {
            FormSelectorOpenMode.POPUPMENU -> showPopupMenu(adapter, holder, item)
            FormSelectorOpenMode.PAGE -> showPage(adapter, holder, item)
            FormSelectorOpenMode.AUTO -> {
                if ((item.options?.size ?: 0) > 30) showPage(adapter, holder, item)
                else showPopupMenu(adapter, holder, item)
            }
        }
    }

    private fun showPopupMenu(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormSelector
    ) {
        val view = holder.getView<MaterialButton>(R.id.formInternalContent)
        val popupMenu = PopupMenu(view.context, view, Gravity.END)
        configPopupMenu(popupMenu, view)
        item.options!!.forEachIndexed { index, option ->
            popupMenu.menu.add(0, index, index, if (item.content == option) {
                SpannableString(option.getName()).apply {
                    setSpan(
                        ForegroundColorSpan(view.attrColor(androidx.appcompat.R.attr.colorPrimary)),
                        0, count(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
            } else option.getName())
        }
        popupMenu.setOnMenuItemClickListener {
            val option =
                item.options!!.getOrNull(it.itemId) ?: return@setOnMenuItemClickListener false
            changeContentAndNotifyLinkage(adapter, item, option)
            view.text = item.getContentText()
            true
        }
        popupMenu.show()
    }

    private fun showPage(adapter: FormPartAdapter, holder: FormViewHolder, item: FormSelector) {
    }

    @Suppress("INACCESSIBLE_TYPE")
    @SuppressLint("RestrictedApi")
    private fun configPopupMenu(popupMenu: PopupMenu, view: MaterialButton) {
        try {
            val mPopupHelper = popupMenu.javaClass.getDeclaredField("mPopup")

            mPopupHelper.isAccessible = true

            val mHelper = mPopupHelper[popupMenu] as MenuPopupHelper
            val standardMenuClass = Class.forName("android.support.v7.view.menu.StandardMenuPopup")
            // 设置不测量item宽度
            val mHasContentWidth = standardMenuClass.getDeclaredField("mHasContentWidth")
            mHasContentWidth.isAccessible = true
            mHasContentWidth.setBoolean(mHelper.popup, true)
            // 设置弹出框宽度
            val mContentWidth = standardMenuClass.getDeclaredField("mContentWidth")
            mContentWidth.isAccessible = true
            mContentWidth.setInt(mHelper.popup, view.width)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onItemRecycler(holder: FormViewHolder) {
        holder.getView<MaterialButton>(R.id.formInternalContent).also {
            if (it.icon is DeterminateDrawable<*>) {
                (it.icon as DeterminateDrawable<*>).stop()
            }
        }
    }
}