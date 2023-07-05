package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormSelector
import com.chooongg.formAdapter.option.FormSelectorPageActivity
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.doOnClick
import com.chooongg.utils.ext.getActivity
import com.chooongg.utils.ext.showToast
import com.chooongg.utils.ext.style
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.DeterminateDrawable
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

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
        iconGravity = MaterialButton.ICON_GRAVITY_TEXT_END
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
//        val itemSelector = item as? FormSelector
//        val drawable = DeterminateDrawable.createCircularDrawable(
//            holder.itemView.context,
//            CircularProgressIndicatorSpec(holder.itemView.context, null)
//        )
        with(holder.getView<MaterialButton>(R.id.formInternalContent)) {
            isEnabled = item.isRealMenuEnable(adapter.formAdapter)
            text = item.getContentText(context)
            hint = item.hint ?: resources.getString(R.string.formDefaultHintSelect)
            gravity = typeset.getContentGravity(adapter, item)
            updateLayoutParams<ViewGroup.LayoutParams> {
                width = typeset.contentWidth()
            }
            doOnClick { onClickButton(adapter, holder, item) }
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
        if (!item.isMust) {
            popupMenu.menu.add(0, 0, 0, (view.hint ?: "").style {
                setForegroundColor(view.attrColor(android.R.attr.textColorHint))
            }.toSpannableString())
        }
        item.options!!.forEachIndexed { index, option ->
            val text = (option.getName()).style {
                setForegroundColor(
                    if (item.content == option) {
                        view.attrColor(androidx.appcompat.R.attr.colorPrimary)
                    } else {
                        view.attrColor(com.google.android.material.R.attr.colorOnSurface)
                    }
                )
            } + " ".style {} + (option.getSecondaryName() ?: "").style {
                setTextSizeRelative(0.8f)
                setForegroundColor(
                    if (item.content == option) {
                        view.attrColor(androidx.appcompat.R.attr.colorPrimary)
                    } else {
                        view.attrColor(com.google.android.material.R.attr.colorOutline)
                    }
                )
            }
            popupMenu.menu.add(0, index + 1, index + 1, text.toSpannableString())
        }
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == 0) {
                changeContentAndNotifyLinkage(adapter, item, null)
                view.text = null
                return@setOnMenuItemClickListener true
            }
            val option =
                item.options!!.getOrNull(it.itemId - 1) ?: return@setOnMenuItemClickListener false
            changeContentAndNotifyLinkage(adapter, item, option)
            view.text = item.getContentText(view.context)
            true
        }
        popupMenu.show()
    }

    private fun showPage(adapter: FormPartAdapter, holder: FormViewHolder, item: FormSelector) {
        val view = holder.getView<MaterialButton>(R.id.formInternalContent)
        val activity = view.context.getActivity()
        val intent = Intent(view.context, FormSelectorPageActivity::class.java)
        intent.putExtra("name", item.name)
        if (item.options.isNullOrEmpty()) {
            showToast(R.string.formDefaultSelectorOptionsEmpty)
            return
        }
        FormSelectorPageActivity.Controller.formSelector = item
        FormSelectorPageActivity.Controller.resultBlock = {
            changeContentAndNotifyLinkage(adapter, item, it)
            view.text = item.getContentText(view.context)
        }
        if (activity != null) {
            activity.setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            activity.startActivity(
                intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, view, "FormSelectorPage"
                ).toBundle()
            )
        } else view.context.startActivity(intent)
    }

    override fun onItemRecycler(holder: FormViewHolder) {
        holder.getView<MaterialButton>(R.id.formInternalContent).also {
            if (it.icon is DeterminateDrawable<*>) {
                (it.icon as DeterminateDrawable<*>).stop()
            }
        }
    }
}