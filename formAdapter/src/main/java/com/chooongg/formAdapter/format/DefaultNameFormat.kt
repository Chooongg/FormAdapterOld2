package com.chooongg.formAdapter.format

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.res.use

object DefaultNameFormat : FormNameFormat() {
    override fun format(context: Context, name: CharSequence?, isMust: Boolean): CharSequence? {
        if (!isMust) return name
        if (name.isNullOrEmpty()) return null
        return SpannableString("⋆ $name").apply {
            val foregroundColorSpan = ForegroundColorSpan(
                context.obtainStyledAttributes(
                    intArrayOf(com.google.android.material.R.attr.colorError)
                ).use { it.getColor(0, Color.GRAY) }
            )
            setSpan(
                foregroundColorSpan,
                0,
                1,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}