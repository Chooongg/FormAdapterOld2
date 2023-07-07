package com.chooongg.formAdapter.provider

import android.content.res.ColorStateList
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormInput
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object FormInputProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = TextInputLayout(
        parent.context, null, com.google.android.material.R.attr.textInputOutlinedStyle
    ).apply {
        id = R.id.formInternalContent
        val editText = TextInputEditText(context).apply {
            id = R.id.formInternalContentChild
            imeOptions = EditorInfo.IME_ACTION_DONE
            isHorizontalFadingEdgeEnabled = true
            isVerticalFadingEdgeEnabled = true
            setFadingEdgeLength(adapter.style.paddingInfo.horizontalGlobal)
            setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
            setPadding(
                0, adapter.style.paddingInfo.verticalLocal,
                0, adapter.style.paddingInfo.verticalLocal
            )
        }
        addView(editText)
        isHintEnabled = false
        boxStrokeWidth = 0
        boxStrokeWidthFocused = 0
        placeholderTextAppearance = R.style.FormAdapter_TextAppearance_Content
        placeholderTextColor = ColorStateList.valueOf(attrColor(android.R.attr.textColorHint))
        setPrefixTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setSuffixTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setEndIconTintList(editText.hintTextColors)
        endIconMinSize = resources.getDimensionPixelSize(R.dimen.formIconSize)
        setPadding(
            adapter.style.paddingInfo.horizontalLocal, 0,
            adapter.style.paddingInfo.horizontalLocal, 0
        )
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewAttachedToWindow(holder: FormViewHolder) {
        with(holder.getView<TextInputLayout>(R.id.formInternalContent)) {
            val temp = isEnabled
            isEnabled = false
            if (temp) isEnabled = true
        }
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemInput = item as? FormInput
        with(holder.getView<TextInputLayout>(R.id.formInternalContent)) {
            isEnabled = item.isRealMenuEnable(adapter.formAdapter)
            suffixText = itemInput?.suffixText
            prefixText = itemInput?.prefixText
            placeholderText = itemInput?.placeholderText
            if (itemInput?.counterLength != null && itemInput.counterLength != Int.MAX_VALUE) {
                if (itemInput.showCounter != false) {
                    isCounterEnabled = true
                    counterMaxLength = itemInput.counterLength
                    getChildAt(1).updatePadding(
                        top = 0,
                        bottom = adapter.style.paddingInfo.verticalLocal
                    )
                } else isCounterEnabled = false
            } else isCounterEnabled = false
        }
        with(holder.getView<TextInputEditText>(R.id.formInternalContentChild)) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            hint = item.hint ?: resources.getString(R.string.formDefaultHintInput)
            setText(item.content as? CharSequence ?: item.getContentText(context))
            gravity = typeset.getContentGravity(adapter, item)
            if (itemInput?.placeholderText != null) {
                setOnFocusChangeListener { _, isFocus ->
                    hint = if (isFocus) {
                        null
                    } else {
                        item.hint ?: resources.getString(R.string.formDefaultHintInput)
                    }
                }
            } else hint = item.hint ?: resources.getString(R.string.formDefaultHintInput)
            if (maxLines <= 1) {
                setSingleLine()
            } else {
                minLines = itemInput?.minLines ?: 0
                maxLines = itemInput?.maxLines ?: Int.MAX_VALUE
            }
            val watcher = doAfterTextChanged { editable ->
                changeContentAndNotifyLinkage(adapter, item, editable)
            }
            tag = watcher
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    adapter.formAdapter.clearFocus()
                    true
                } else false
            }
        }
    }
}