package com.chooongg.formAdapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var _helper: WeakReference<FormHelper>? = null

    var helper: FormHelper?
        get() = _helper?.get()
        set(value) {
            if (value == null){
                _helper = null
                adapter = null
            }else{
                _helper = WeakReference(value)
                adapter = _helper!!.get()?.adapter
            }
        }

    init {
        layoutManager = LinearLayoutManager(context)
    }

    override fun setAdapter(adapter: Adapter<*>?) = Unit
    override fun getAdapter(): Adapter<*>? = null
}