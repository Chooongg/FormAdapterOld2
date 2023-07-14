package com.chooongg.formAdapter.item

import android.icu.text.DateFormat
import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.enum.FormTimeMode
import com.chooongg.formAdapter.provider.FormTextProvider
import com.chooongg.formAdapter.provider.FormTimeProvider
import com.chooongg.utils.TimeUtils
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DayViewDecorator
import com.google.android.material.timepicker.TimeFormat
import java.util.Locale

fun FormCreator.addTime(
    name: CharSequence?, field: String? = null, block: (FormTime.() -> Unit)? = null
) = add(FormTime(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addTime(
    @StringRes nameRes: Int?, field: String? = null, block: (FormTime.() -> Unit)? = null
) = add(FormTime(nameRes, null, field).apply { block?.invoke(this) })

class FormTime(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {
//    companion object {
//        const val DEFAULT_OUTPUT_FORMAT_TIME = "HH:mm:ss"
//        const val DEFAULT_OUTPUT_FORMAT_DATE = "yyyy-MM-dd"
//        const val DEFAULT_OUTPUT_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
//    }

    var timeMode: FormTimeMode = FormTimeMode.DATE_TIME

    var showFormatPattern: String? = null

    var calendarConstraints: CalendarConstraints? = null

    var dayViewDecorator: DayViewDecorator? = null

    @TimeFormat
    var timeFormatMode: Int = TimeFormat.CLOCK_24H

    var outputFormatPattern: String? = null

    override fun getItemProvider(adapter: FormAdapter) =
        if (isRealEnable(adapter)) FormTimeProvider else FormTextProvider

    override fun getContentText(adapter: FormPartAdapter, holder: FormViewHolder): CharSequence? {
        val millis = content as? Long ?: return content?.toString()
        if (showFormatPattern != null) return TimeUtils.millis2String(millis, showFormatPattern!!)
        return when (timeMode) {
            FormTimeMode.TIME -> DateFormat.getTimeInstance(
                DateFormat.SHORT, Locale.getDefault()
            )

            FormTimeMode.DATE -> DateFormat.getDateInstance(
                DateFormat.DEFAULT, Locale.getDefault()
            )

            FormTimeMode.DATE_TIME -> DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.SHORT, Locale.getDefault()
            )
        }.format(millis)
    }
}