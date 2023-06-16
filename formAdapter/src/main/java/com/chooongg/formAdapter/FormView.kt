package com.chooongg.formAdapter

import android.content.Context
import android.util.AttributeSet
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
            if (value == null) {
                _helper = null
                super.setAdapter(null)
            } else {
                _helper = WeakReference(value)
                super.setAdapter(value.adapter)
            }
        }

    init {
        layoutManager = FormLayoutManager(context)
        val helperTemp = helper
        if (helperTemp != null) setFormHelper(helperTemp)
    }

    @Deprecated("Use helper instead", ReplaceWith("helper"))
    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
    }
}