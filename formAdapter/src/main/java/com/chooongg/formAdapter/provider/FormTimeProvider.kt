package com.chooongg.formAdapter.provider

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.enum.FormTimeMode
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormTime
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.doOnClick
import com.chooongg.utils.ext.getActivity
import com.chooongg.utils.ext.showToast
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.Calendar

object FormTimeProvider : BaseFormProvider() {
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
        iconSize = resources.getDimensionPixelSize(com.chooongg.formAdapter.R.dimen.formIconSize)
        iconTint = hintTextColors
        iconGravity = MaterialButton.ICON_GRAVITY_END
        setIconResource(R.drawable.ic_form_arrow_down)
        setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
        setPadding(
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal,
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal
        )
        layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.getView<MaterialButton>(R.id.formInternalContent)) {
            isEnabled = item.isRealMenuEnable(adapter.formAdapter)
            text = item.getContentText(context)
            hint = item.hint
                ?: resources.getString(com.chooongg.formAdapter.R.string.formDefaultHintSelect)
            gravity = typeset.getContentGravity(adapter, item)
            updateLayoutParams<ViewGroup.LayoutParams> {
                width = typeset.contentWidth()
            }
            doOnClick { onClickButton(adapter, this, item) }
        }
    }

    private fun onClickButton(adapter: FormPartAdapter, anchor: MaterialButton, item: BaseForm) {
        if (item !is FormTime) return
        val activity = anchor.context.getActivity() as? AppCompatActivity
        if (activity == null) {
            showToast(R.string.formPickerOpenError)
            return
        }
        val calendar = Calendar.getInstance()
        val content = item.content as? Long
        if (content != null) {
            calendar.timeInMillis = content
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
        }
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        when (item.timeMode) {
            FormTimeMode.TIME -> MaterialTimePicker.Builder().setTitleText(item.name)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTimeFormat(item.timeFormatMode)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build().apply {
                    addOnPositiveButtonClickListener {
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)
                        changeContentAndNotifyLinkage(adapter, item, calendar.timeInMillis)
                        anchor.text = item.getContentText(anchor.context)
                    }
                }.show(activity.supportFragmentManager, "FormTimePicker")

            FormTimeMode.DATE -> MaterialDatePicker.Builder.datePicker().setTitleText(item.name)
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setSelection(calendar.timeInMillis + calendar.timeZone.rawOffset)
                .setCalendarConstraints(item.calendarConstraints)
                .setDayViewDecorator(item.dayViewDecorator)
                .build().apply {
                    addOnPositiveButtonClickListener {
                        calendar.timeInMillis = it - calendar.timeZone.rawOffset
                        changeContentAndNotifyLinkage(adapter, item, calendar.timeInMillis)
                        anchor.text = item.getContentText(anchor.context)
                    }
                }.show(activity.supportFragmentManager, "FormDatePicker")

            FormTimeMode.DATE_TIME -> {
                val newCalendar = Calendar.getInstance()
                newCalendar.timeInMillis = calendar.timeInMillis
                MaterialDatePicker.Builder.datePicker().setTitleText(item.name)
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .setSelection(newCalendar.timeInMillis + newCalendar.timeZone.rawOffset)
                    .setCalendarConstraints(item.calendarConstraints)
                    .setDayViewDecorator(item.dayViewDecorator)
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            newCalendar.timeInMillis = it - newCalendar.timeZone.rawOffset

                            val isChangeDay = when {
                                newCalendar.get(Calendar.YEAR) != calendar.get(Calendar.YEAR) -> true
                                newCalendar.get(Calendar.MONTH) != calendar.get(Calendar.MONTH) -> true
                                newCalendar.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH) -> true
                                else -> false
                            }
                            if (isChangeDay) {
                                newCalendar.set(Calendar.HOUR_OF_DAY, 0)
                                newCalendar.set(Calendar.MINUTE, 0)
                            } else {
                                newCalendar.set(
                                    Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
                                )
                                newCalendar.set(
                                    Calendar.MINUTE, calendar.get(Calendar.MINUTE)
                                )
                            }
                            MaterialTimePicker.Builder().setTitleText(item.name)
                                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                                .setTimeFormat(item.timeFormatMode)
                                .setHour(newCalendar.get(Calendar.HOUR_OF_DAY))
                                .setMinute(newCalendar.get(Calendar.MINUTE))
                                .build().apply {
                                    addOnPositiveButtonClickListener {
                                        newCalendar.set(Calendar.HOUR_OF_DAY, hour)
                                        newCalendar.set(Calendar.MINUTE, minute)
                                        calendar.timeInMillis = newCalendar.timeInMillis
                                        changeContentAndNotifyLinkage(
                                            adapter, item, calendar.timeInMillis
                                        )
                                        anchor.text = item.getContentText(anchor.context)
                                    }
                                }.show(activity.supportFragmentManager, "FormTimePicker")
                        }
                    }.show(activity.supportFragmentManager, "FormDatePicker")
            }
        }
    }
}