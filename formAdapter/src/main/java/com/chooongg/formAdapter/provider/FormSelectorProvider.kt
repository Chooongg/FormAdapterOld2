package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.content.Intent
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.TooltipCompat
import androidx.core.app.ActivityOptionsCompat
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormSelector
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.option.FormSelectorPageActivity
import com.chooongg.formAdapter.option.OptionResult
import com.chooongg.formAdapter.option.OptionState
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.SpannableStyle
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.doOnClick
import com.chooongg.utils.ext.dp2px
import com.chooongg.utils.ext.getActivity
import com.chooongg.utils.ext.resString
import com.chooongg.utils.ext.showToast
import com.chooongg.utils.ext.style
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import java.lang.reflect.Field

object FormSelectorProvider : BaseFormProvider() {

    override fun onCreateItemView(
        partAdapter: FormPartAdapter, typeset: Typeset, parent: ViewGroup
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
        iconTint = hintTextColors
        iconGravity = MaterialButton.ICON_GRAVITY_END
        setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setPadding(
            partAdapter.style.paddingInfo.horizontalLocal,
            partAdapter.style.paddingInfo.verticalLocal,
            partAdapter.style.paddingInfo.horizontalLocal,
            partAdapter.style.paddingInfo.verticalLocal
        )
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        )
    }

    @SuppressLint("ResourceType")
    override fun onBindItemView(
        partAdapter: FormPartAdapter, typeset: Typeset, holder: FormViewHolder, item: BaseForm
    ) {
        val itemSelector = item as? FormSelector
        configOption(holder, itemSelector)
        with(holder.getView<MaterialButton>(R.id.formInternalContent)) {
            isEnabled = item.isRealMenuEnable(partAdapter.formAdapter)
            text = item.getContentText(partAdapter, holder)
            hint = item.hint ?: resources.getString(R.string.formDefaultHintSelect)
            gravity = typeset.getContentGravity(partAdapter, item)
            doOnClick { onClickButton(partAdapter, holder, item) }
        }
        loadOption(partAdapter, holder, itemSelector)
    }

    override fun onBindItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindItemView(partAdapter, typeset, holder, item, payloads)
            return
        }
        if (payloads.contains("changeOption")) {
            configOption(holder, item as? FormSelector)
        }
    }

    private fun loadOption(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormSelector?
    ) {
        if (item?.isNeedToLoadOption(holder) == true) {
            item.loadOption(adapter, holder) {
                val position = adapter.indexOfPosition(item)
                if (position != -1) {
                    holder.itemView.post {
                        adapter.notifyItemChanged(adapter.indexOfPosition(item), "changeOption")
                    }
                }
            }
        }
    }

    private fun configOption(holder: FormViewHolder, item: FormSelector?) {
        with(holder.getView<MaterialButton>(R.id.formInternalContent)) {
            if (item == null) {
                TooltipCompat.setTooltipText(this, null)
                icon = null
                return@with
            }
            when (item.optionResult) {
                is OptionState.Loading -> {
                    TooltipCompat.setTooltipText(this, resString(R.string.formOptionsLoading))
                    icon = IndeterminateDrawable.createCircularDrawable(
                        context, CircularProgressIndicatorSpec(context, null).apply {
                            trackThickness = dp2px(1.5f)
                            indicatorSize = iconSize / 2
                            indicatorColors = intArrayOf(attrColor(android.R.attr.textColorHint))
                        })
                }

                is OptionResult.Error -> {
                    TooltipCompat.setTooltipText(
                        this, (item.optionResult as OptionResult.Error<BaseOption>).e.message
                    )
                    setIconResource(R.drawable.ic_form_error)
                }

                is OptionResult.Empty -> {
                    TooltipCompat.setTooltipText(this, resString(R.string.formOptionsEmpty))
                    icon = null
                }

                else -> if (item.options.isNullOrEmpty()) {
                    TooltipCompat.setTooltipText(this, resString(R.string.formOptionsEmpty))
                    icon = null
                } else {
                    TooltipCompat.setTooltipText(this, null)
                    setIconResource(R.drawable.ic_form_arrow_down)
                }
            }
        }
    }

    private fun onClickButton(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        if (item !is FormSelector) return
        if (!item.options.isNullOrEmpty()) show(adapter, holder, item)
        else {
            if (item.optionResult is OptionState.Loading) {
                showToast(R.string.formOptionsLoading)
            } else {
                loadOption(adapter, holder, item)
            }
        }
    }

    private fun show(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormSelector
    ) {
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

    @Suppress("INACCESSIBLE_TYPE")
    @SuppressLint("RestrictedApi")
    private fun showPopupMenu(
        adapter: FormPartAdapter, holder: FormViewHolder, item: FormSelector
    ) {
        val view = holder.getView<MaterialButton>(R.id.formInternalContent)
        val popupMenu = PopupMenu(view.context, view)
        if (!item.isMust) {
            popupMenu.menu.add(0, 0, 0, (view.hint ?: "").style {
                setForegroundColor(view.attrColor(android.R.attr.textColorHint))
            }.toSpannableString())
        }
        item.options!!.forEachIndexed { index, option ->
            val span = SpannableStyle(option.getName()).apply {
                if (option == item.content) {
                    setForegroundColor(view.attrColor(androidx.appcompat.R.attr.colorPrimary))
                } else {
                    setForegroundColor(view.attrColor(com.google.android.material.R.attr.colorOnSurface))
                }
            }
            val secondaryName = option.getSecondaryName()
            if (secondaryName != null) {
                span.plus(" ")
                span.plus(secondaryName.style {
                    setTextSizeRelative(0.8f)
                    if (option == item.content) {
                        setForegroundColor(view.attrColor(androidx.appcompat.R.attr.colorPrimary))
                    } else {
                        setForegroundColor(view.attrColor(com.google.android.material.R.attr.colorOutline))
                    }
                })
            }
            popupMenu.menu.add(0, index + 1, index + 1, span.toSpannableString())
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
            view.text = item.getContentText(adapter, holder)
            true
        }
        try {
            val mPopupMenu: Field = popupMenu.javaClass.getDeclaredField("mPopup")
            mPopupMenu.isAccessible = true
            val mHelper: MenuPopupHelper = mPopupMenu.get(popupMenu) as MenuPopupHelper
            val standardMenuClass: Class<*> =
                Class.forName("androidx.appcompat.view.menu.StandardMenuPopup")
            //设置不测量Item宽度
            val mHasContentWidth: Field = standardMenuClass.getDeclaredField("mHasContentWidth")
            mHasContentWidth.isAccessible = true
            mHasContentWidth.setBoolean(mHelper.popup, true)
            //设置弹出宽度
            val mContentWidth: Field = standardMenuClass.getDeclaredField("mContentWidth")
            mContentWidth.isAccessible = true
            //这里第二个参数填自己想要的宽度即可，获取屏幕宽度也行或者父控件
            mContentWidth.setInt(mHelper.popup, view.width)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            popupMenu.show()
        }
    }

    private fun showPage(adapter: FormPartAdapter, holder: FormViewHolder, item: FormSelector) {
        val view = holder.getView<MaterialButton>(R.id.formInternalContent)
        val activity = view.context.getActivity()
        val intent = Intent(view.context, FormSelectorPageActivity::class.java)
        intent.putExtra("name", item.name)
        FormSelectorPageActivity.Controller.formSelector = item
        FormSelectorPageActivity.Controller.resultBlock = {
            changeContentAndNotifyLinkage(adapter, item, it)
            view.text = item.getContentText(adapter, holder)
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
        holder.job?.cancel()
        holder.getView<MaterialButton>(R.id.formInternalContent).also {
            if (it.icon is IndeterminateDrawable<*>) {
                (it.icon as IndeterminateDrawable<*>).stop()
            }
        }
    }
}