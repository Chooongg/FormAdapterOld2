package com.chooongg.formAdapter.provider

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormInputAutoComplete
import com.chooongg.formAdapter.option.OptionResult
import com.chooongg.formAdapter.option.OptionState
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.dp2px
import com.chooongg.utils.ext.logE
import com.chooongg.utils.ext.resString
import com.chooongg.utils.ext.showToast
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.min

object FormInputAutoCompleteProvider : BaseFormProvider() {

    override fun onCreateItemView(
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = TextInputLayout(
        parent.context, null, com.google.android.material.R.attr.textInputOutlinedStyle
    ).apply {
        id = R.id.formInternalContent
        val editText = MaterialAutoCompleteTextView(
            ContextThemeWrapper(
                context,
                com.google.android.material.R.style.ThemeOverlay_Material3_AutoCompleteTextView_OutlinedBox
            ),
        ).apply {
            id = R.id.formInternalContentChild
            imeOptions = EditorInfo.IME_ACTION_DONE
            isHorizontalFadingEdgeEnabled = true
            isVerticalFadingEdgeEnabled = true
            setFadingEdgeLength(partAdapter.style.paddingInfo.horizontalGlobal)
            setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
            setPadding(
                0, partAdapter.style.paddingInfo.verticalLocal,
                0, partAdapter.style.paddingInfo.verticalLocal
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
        findViewById<View>(com.google.android.material.R.id.text_input_end_icon).background =
            RippleDrawable(
                ColorStateList.valueOf(attrColor(androidx.appcompat.R.attr.colorControlHighlight)),
                null,
                null
            ).apply {
                radius = endIconMinSize / 2 + min(
                    partAdapter.style.paddingInfo.horizontalLocal,
                    partAdapter.style.paddingInfo.verticalLocal
                )
            }
        endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
        setEndIconDrawable(R.drawable.ic_form_arrow_down)
        setPadding(
            partAdapter.style.paddingInfo.horizontalLocal, 0,
            partAdapter.style.paddingInfo.horizontalLocal, 0
        )
        layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT
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
        partAdapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemInput = item as? FormInputAutoComplete
        configOptions(partAdapter, holder, itemInput)
        with(holder.getView<TextInputLayout>(R.id.formInternalContent)) {
            isEnabled = item.isRealMenuEnable(partAdapter.formAdapter)
            suffixText = itemInput?.suffixText
            prefixText = itemInput?.prefixText
            placeholderText = itemInput?.placeholderText
            if (itemInput?.maxLength != null && itemInput.maxLength != Int.MAX_VALUE && itemInput.isShowCounter != false) {
                isCounterEnabled = true
                counterMaxLength = itemInput.maxLength
                getChildAt(1).updatePadding(
                    top = 0, bottom = partAdapter.style.paddingInfo.verticalLocal
                )
            } else isCounterEnabled = false
        }
        with(holder.getView<MaterialAutoCompleteTextView>(R.id.formInternalContentChild)) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            hint = item.hint ?: resources.getString(R.string.formDefaultHintInput)
            setText(item.content as? CharSequence ?: item.getContentText(partAdapter, holder))
            gravity = typeset.getContentGravity(partAdapter, item)
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
                changeContentAndNotifyLinkage(partAdapter, item, editable)
            }
            tag = watcher
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    partAdapter.formAdapter.clearFocus()
                    true
                } else false
            }
        }
        loadOption(partAdapter, holder, itemInput)
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
            configOptions(partAdapter, holder, item as? FormInputAutoComplete)
        }
    }

    private fun loadOption(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormInputAutoComplete?
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

    private fun configOptions(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        item: FormInputAutoComplete?
    ) {
        val layout = holder.getView<TextInputLayout>(R.id.formInternalContent)
        val end = layout.findViewById<View>(com.google.android.material.R.id.text_input_end_icon)
        val editText = holder.getView<MaterialAutoCompleteTextView>(R.id.formInternalContentChild)
        with(editText) {
            if (item?.options.isNullOrEmpty()) {
                setAdapter(null)
            } else {
                setAdapter(FormArrayAdapter(context, item!!.options!!))
            }
        }
        with(layout) {
            if (item == null) {
                TooltipCompat.setTooltipText(end, null)
                endIconDrawable = null
                return
            }
            when (item.optionResult) {
                is OptionState.Loading -> {
                    TooltipCompat.setTooltipText(end, resString(R.string.formOptionsLoading))
                    val drawable = IndeterminateDrawable.createCircularDrawable(
                        context,
                        CircularProgressIndicatorSpec(context, null).apply {
                            trackThickness = dp2px(1.5f)
                            indicatorSize = endIconMinSize / 2
                            indicatorColors = intArrayOf(attrColor(android.R.attr.textColorHint))
                        }
                    )
                    endIconDrawable = drawable
                    drawable.start()
                    setEndIconOnClickListener { showToast(R.string.formOptionsLoading) }
                }

                is OptionResult.Error -> {
                    TooltipCompat.setTooltipText(
                        end, (item.optionResult as OptionResult.Error<String>).e.message
                    )
                    setEndIconDrawable(R.drawable.ic_form_error)
                    setEndIconOnClickListener {
                        loadOption(adapter, holder, item)
                    }
                }

                is OptionResult.Empty -> {
                    TooltipCompat.setTooltipText(end, resString(R.string.formOptionsEmpty))
                    endIconDrawable = null
                    setEndIconOnClickListener { showToast(R.string.formOptionsEmpty) }
                }

                else -> {
                    TooltipCompat.setTooltipText(end, null)
                    setEndIconDrawable(R.drawable.ic_form_arrow_down)
                    setEndIconOnClickListener { editText.showDropDown() }
                }
            }
        }
    }

    private class FormArrayAdapter<T>(
        context: Context, list: List<T>?
    ) : BaseAdapter(), Filterable {

        private val mLock = Any()

        private val inflater = LayoutInflater.from(context)

        private var mOriginalValues: ArrayList<T> = ArrayList()

        private var mObjects: ArrayList<T>

        init {
            mObjects = if (list == null) ArrayList() else ArrayList(list)
        }

        override fun getCount(): Int = mObjects.size

        override fun getItem(position: Int): T = mObjects[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: inflater.inflate(
                R.layout.form_item_auto_complete_simple, parent, false
            )
            val text = view as TextView
            val item = getItem(position)
            text.text = if (item is CharSequence) item else item.toString()
            return view
        }

        override fun getFilter(): Filter = FormFilter()

        private inner class FormFilter : Filter() {
            override fun performFiltering(prefix: CharSequence?): FilterResults {
                val results = FilterResults()
                if (mOriginalValues.isEmpty()) {
                    synchronized(mLock) {
                        mOriginalValues.addAll(mObjects)
                    }
                }
                if (prefix.isNullOrEmpty()) {
                    synchronized(mLock) {
                        val list = ArrayList<T>(mOriginalValues)
                        results.values = list
                        results.count = list.size
                    }
                } else {
                    val newValues = synchronized(mLock) {
                        mOriginalValues.filter {
                            val regex = Regex(".*${prefix.toString().lowercase()}.*")
                            val value = it?.toString() ?: return@filter false
                            regex.matches(value.lowercase())
                        }
                    }
                    results.values = newValues
                    results.count = newValues.size
                }
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                mObjects = results.values as ArrayList<T>
                if (results.count > 0) notifyDataSetChanged() else notifyDataSetInvalidated()
            }
        }
    }

    override fun onItemRecycler(holder: FormViewHolder) {
        holder.job?.cancel()
    }
}