package com.chooongg.formAdapter

import android.util.Log
import android.view.ViewGroup
import androidx.collection.ArraySet
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.chooongg.formAdapter.provider.BaseFormProvider
import com.chooongg.formAdapter.style.Style
import com.chooongg.formAdapter.typeset.Typeset
import java.lang.ref.WeakReference

open class FormConcatAdapter constructor(isEditable: Boolean) : RecyclerView.Adapter<ViewHolder>() {

    private val concatAdapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            updateForm()
        }

    val adapters = concatAdapter.adapters.map { it as FormPartAdapter }

    internal var _recyclerView: WeakReference<RecyclerView>? = null

    internal var paddingVertical: Int = 0
    internal var paddingHorizontal: Int = 0

    fun plusPart(adapter: FormPartAdapter) {
        concatAdapter.addAdapter(adapter)
    }

    fun plusPart(index: Int, adapter: FormPartAdapter) {
        concatAdapter.addAdapter(index, adapter)
    }

    fun removePart(adapter: FormPartAdapter) {
        concatAdapter.removeAdapter(adapter)
    }

    fun partIndexOf(part: FormPartAdapter) = concatAdapter.adapters.indexOf(part)

    fun partSize() = concatAdapter.adapters.size

    fun updateForm() {
        adapters.forEach { it.update() }
    }

    fun findRelativeAdapterPositionIn(
        adapter: FormPartAdapter,
        viewHolder: ViewHolder,
        localPosition: Int
    ): Int {
        return concatAdapter.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition)
    }

    fun getWrappedAdapterAndPosition(globalPosition: Int): Pair<FormPartAdapter, Int> {
        concatAdapter.getWrappedAdapterAndPosition(globalPosition).let {
            return Pair(it.first as FormPartAdapter, it.second)
        }
    }

    //<editor-fold desc="覆写">

    override fun getItemViewType(position: Int): Int {
        return concatAdapter.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return concatAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        concatAdapter.onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        concatAdapter.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemId(position: Int): Long {
        return concatAdapter.getItemId(position)
    }

    override fun getItemCount(): Int {
        return concatAdapter.itemCount
    }

    override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
        return concatAdapter.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        concatAdapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        concatAdapter.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        concatAdapter.onViewRecycled(holder)
    }

    private val onScrollListener = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING && recyclerView.focusedChild != null) {
                recyclerView.focusedChild.clearFocus()
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
        if (recyclerView.layoutManager !is FormLayoutManager) {
            recyclerView.layoutManager = FormLayoutManager(recyclerView.context)
        }
        concatAdapter.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(onScrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = null
        concatAdapter.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(onScrollListener)
    }

    //</editor-fold>

    //<editor-fold desc="ItemViewType缓存池">

    private val stylePool = ArrayList<Style>()
    private val typesetPool = ArrayList<Typeset>()
    private val itemProviderPool = ArrayList<BaseFormProvider>()
    private val itemTypePool = ArraySet<Triple<Int, Int, Int>>()

    internal fun getItemViewType(style: Style, typeset: Typeset, provider: BaseFormProvider): Int {
        if (!stylePool.contains(style)) stylePool.add(style)
        val styleIndex = stylePool.indexOf(style)

        if (!typesetPool.contains(typeset)) typesetPool.add(typeset)
        val typesetIndex = typesetPool.indexOf(typeset)

        if (!itemProviderPool.contains(provider)) itemProviderPool.add(provider)
        val providerIndex = itemProviderPool.indexOf(provider)

        val type = Triple(styleIndex, typesetIndex, providerIndex)
        Log.e(
            "ItemViewType",
            "${type}\n${style.javaClass.simpleName}   ${typeset.javaClass.simpleName}   ${provider.javaClass.simpleName}"
        )
        itemTypePool.add(type)
        return itemTypePool.indexOf(type)
    }

    internal fun getTypesetForItemViewType(viewType: Int): Typeset {
        val triple = itemTypePool.valueAt(viewType) ?: throw RuntimeException("No such viewType")
        return typesetPool[triple.second]
    }

    internal fun getItemProviderForItemViewType(viewType: Int): BaseFormProvider {
        val triple = itemTypePool.valueAt(viewType) ?: throw RuntimeException("No such viewType")
        return itemProviderPool[triple.third]
    }

    //</editor-fold>
}