package com.chooongg.formAdapter.format

import android.content.Context

interface FormNameFormat {
    fun format(context: Context, name: CharSequence?, isMust: Boolean): CharSequence?
}