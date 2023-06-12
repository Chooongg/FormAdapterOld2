package com.chooongg.formAdapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val concatAdapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = concatAdapter
    }

    fun update(){
        concatAdapter.adapters.forEach {

        }
    }
}