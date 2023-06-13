package com.chooongg.formAdapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.data.PartCreator
import com.chooongg.formAdapter.style.NoneStyle
import com.chooongg.formAdapter.style.Style
import kotlin.math.max

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val globalAdapter = ConcatAdapter()

    private var headerAdapterCount = 0
    private var footerAdapterCount = 0

    init {
        layoutManager = LinearLayoutManager(context)
        super.setAdapter(globalAdapter)
    }

//    fun setNewInstance(creator: FormCreator.() -> Unit) {
//        setNewInstance(FormCreator().apply(creator))
//    }
//
//    fun setNewInstance(creator: FormCreator) {
//
//    }

    fun addPart(style: Style = NoneStyle, block: PartCreator.() -> Unit) {
        addPart(FormPartAdapter(style).apply {
            submit(block)
        })
    }

    fun addPart(part: FormPartAdapter) {
        globalAdapter.addAdapter(headerAdapterCount, part)
    }

    fun addHeaderAdapter(adapter: Adapter<*>) {
        val firstPartIndex = globalAdapter.adapters.indexOfFirst { it is FormPartAdapter }
        globalAdapter.addAdapter(max(0, firstPartIndex), adapter)
        headerAdapterCount++
    }

    fun removeHeaderAdapter(adapter: Adapter<*>) {
        globalAdapter.removeAdapter(adapter)
        headerAdapterCount--
    }

    fun addFooterAdapter(adapter: Adapter<*>) {
        globalAdapter.addAdapter(adapter)
        footerAdapterCount++
    }

    fun removeFooterAdapter(adapter: Adapter<*>) {
        globalAdapter.removeAdapter(adapter)
        footerAdapterCount--
    }

    override fun setAdapter(adapter: Adapter<*>?) = Unit
    override fun getAdapter(): Adapter<*>? = null
}