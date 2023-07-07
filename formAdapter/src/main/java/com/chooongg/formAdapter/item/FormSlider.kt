package com.chooongg.formAdapter.item

import android.content.Context
import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormSliderFormatter
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormSliderProvider

fun FormCreator.addSlider(
    name: CharSequence?, field: String? = null, block: (FormSlider.() -> Unit)? = null
) = add(FormSlider(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addSlider(
    @StringRes nameRes: Int?, field: String? = null, block: (FormSlider.() -> Unit)? = null
) = add(FormSlider(nameRes, null, field).apply { block?.invoke(this) })

class FormSlider(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

    var valueFrom: Float = 0f

    var valueTo: Float = 100f

    var stepSize: Float = 0f

    private var formatter: FormSliderFormatter? = null

    fun formatter(block: FormSliderFormatter?) {
        formatter = block
    }

    fun getFormatter() = formatter

    override fun getItemProvider(adapter: FormAdapter) = FormSliderProvider

    override fun getContentText(context: Context): CharSequence? {
        val value = content as? Float ?: return content?.toString()
        return if (formatter != null) {
            formatter?.invoke(value)
        } else value.toString()
    }
}