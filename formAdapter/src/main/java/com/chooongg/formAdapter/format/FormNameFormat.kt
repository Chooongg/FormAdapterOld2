package com.chooongg.formAdapter.format

import android.content.Context

abstract class FormNameFormat {
    abstract fun format(context: Context, name: CharSequence?, isMust: Boolean): CharSequence?
}