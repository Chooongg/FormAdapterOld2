package com.chooongg.formAdapter

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setFormHelper(helper: FormHelper) {
    adapter = helper.adapter
}