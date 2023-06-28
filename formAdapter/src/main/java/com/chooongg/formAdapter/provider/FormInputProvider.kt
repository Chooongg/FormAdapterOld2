package com.chooongg.formAdapter.provider

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormInput
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.dp2px
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object FormInputProvider : BaseFormProvider() {
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = TextInputLayout(
        parent.context,
        null,
        com.google.android.material.R.attr.textInputOutlinedStyle
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
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal,
                adapter.style.paddingInfo.horizontalLocal,
                adapter.style.paddingInfo.verticalLocal
            )
        }
        addView(editText)
//        isHintEnabled = false
        setBoxBackgroundColorStateList(ColorStateList.valueOf(Color.TRANSPARENT))
//        boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
//        boxStrokeWidthFocused = 0
//        boxStrokeWidth = 0
        setPrefixTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setSuffixTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setEndIconTintList(editText.hintTextColors)
        layoutParams = MarginLayoutParams(
            MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<TextInputLayout>(R.id.formInternalContent)) {
            updateInputLayoutStyle(adapter, this, item)
            if ((item as? FormInput)?.enableAnimationHint == true) {
                isHintEnabled = true
                hint = item.hint ?: resources.getString(R.string.formDefaultHintInput)
            } else {
                isHintEnabled = false
                hint = null
            }

            suffixText = (item as? FormInput)?.suffixText
            prefixText = (item as? FormInput)?.prefixText
            placeholderText = (item as? FormInput)?.placeholderText
        }
        with(holder.getView<TextInputEditText>(R.id.formInternalContentChild)) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            setText(item.getContentText())
            hint = if ((item as? FormInput)?.enableAnimationHint != true) {
                item.hint ?: resources.getString(R.string.formDefaultHintInput)
            } else null

            gravity = getContentGravity(adapter, typeset, item)
            minLines = (item as? FormInput)?.minLines ?: 0
            maxLines = (item as? FormInput)?.maxLines ?: Int.MAX_VALUE
            isSingleLine = maxLines <= 1
            val watcher = doAfterTextChanged { editable ->
                item.content = editable
                // TODO 更新Content通知
//                adapter.globalAdapter.listener?.onFormContentChanged(adapter, this, it)
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

    private fun updateInputLayoutStyle(
        adapter: FormPartAdapter,
        view: TextInputLayout,
        item: BaseForm
    ) {
        val backgroundMode =
            (item as? FormInput)?.backgroundMode ?: TextInputLayout.BOX_BACKGROUND_NONE
        view.boxBackgroundMode = if (backgroundMode == TextInputLayout.BOX_BACKGROUND_NONE) {
            TextInputLayout.BOX_BACKGROUND_OUTLINE
        } else backgroundMode
        when (backgroundMode) {
            TextInputLayout.BOX_BACKGROUND_NONE -> {
                view.boxStrokeWidth = 0
                view.boxStrokeWidthFocused = 0
                view.updateLayoutParams<MarginLayoutParams> {
                    setMargins(0)
                }
            }

            TextInputLayout.BOX_BACKGROUND_OUTLINE -> {
                view.boxStrokeWidth = dp2px(1f)
                view.boxStrokeWidthFocused = dp2px(2f)
                view.updateLayoutParams<MarginLayoutParams> {
                    setMargins(0)
                }
            }

            TextInputLayout.BOX_BACKGROUND_FILLED -> {
                view.boxStrokeWidth = 0
                view.boxStrokeWidthFocused = 0
                view.setBoxBackgroundColorStateList(
                    (item as? FormInput)?.backgroundColor?.invoke(view.context)
                        ?: ColorStateList.valueOf(Color.TRANSPARENT)
                )
                view.updateLayoutParams<MarginLayoutParams> {
                    setMargins(
                        adapter.style.paddingInfo.horizontalLocal,
                        adapter.style.paddingInfo.verticalLocal,
                        adapter.style.paddingInfo.horizontalLocal,
                        adapter.style.paddingInfo.verticalLocal
                    )
                }
            }
        }
    }
}