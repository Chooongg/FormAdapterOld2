package com.chooongg.formAdapter.provider

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormSlider
import com.chooongg.formAdapter.typeset.Typeset
import com.chooongg.utils.ext.dp2px
import com.google.android.material.slider.Slider
import com.google.android.material.textview.MaterialTextView
import kotlin.math.min

object FormSliderProvider : BaseFormProvider() {

    @SuppressLint("RestrictedApi")
    override fun onCreateItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        parent: ViewGroup
    ) = Slider(parent.context).apply {
        id = R.id.formInternalContent
        val textHeight = with(MaterialTextView(context)) {
            setTextAppearance(R.style.FormAdapter_TextAppearance_Content)
            measure(0, 0)
            measuredHeight
        }
        setPadding(
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal,
            adapter.style.paddingInfo.horizontalLocal,
            adapter.style.paddingInfo.verticalLocal
        )
        val height =
            textHeight + adapter.style.paddingInfo.verticalLocal * 2
        try {
            val widgetHeight = javaClass.superclass.getDeclaredField("widgetHeight")
            widgetHeight.isAccessible = true
            widgetHeight.setInt(this, height)
        } catch (_: Exception) {
        }
        haloRadius = min(height / 2, dp2px(24f))
    }

    override fun onBindItemView(
        adapter: FormPartAdapter,
        typeset: Typeset,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        val itemSlider = item as? FormSlider
        with(holder.getView<Slider>(R.id.formInternalContent)) {
            if (tag is Slider.OnSliderTouchListener) {
                removeOnSliderTouchListener(tag as Slider.OnSliderTouchListener)
            }
            isEnabled = item.isRealEnable(adapter.formAdapter)
            valueFrom = itemSlider?.valueFrom ?: 0f
            valueTo = itemSlider?.valueTo ?: 100f
            stepSize = itemSlider?.stepSize ?: 0f
            val content = item.content as? Float
            value = content ?: 0f
            setLabelFormatter(itemSlider?.getFormatter())
            val listener = object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) = Unit
                override fun onStopTrackingTouch(slider: Slider) {
                    changeContentAndNotifyLinkage(adapter, item, slider.value)
                }
            }
            addOnSliderTouchListener(listener)
            tag = listener
        }
    }
}