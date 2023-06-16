package com.chooongg.formAdapter

import androidx.collection.ArraySet
import androidx.recyclerview.widget.ConcatAdapter
import com.chooongg.formAdapter.data.PartData
import com.chooongg.formAdapter.provider.BaseFormProvider
import com.chooongg.formAdapter.style.NoneStyle
import com.chooongg.formAdapter.style.Style
import com.chooongg.formAdapter.typeset.Typeset

class FormHelper(isEditable: Boolean, block: (FormHelper.() -> Unit)?) {

    internal val adapter = ConcatAdapter()

    var isEditable: Boolean = isEditable
        set(value) {
            field = value
            updateForm()
        }

    init {
        block?.invoke(this)
    }

    fun createPart(vararg part: FormPartAdapter) {
        adapter.adapters.forEach { adapter.removeAdapter(it) }
        part.forEach { adapter.addAdapter(it) }
    }

    fun plusPart(style: Style = NoneStyle, block: PartData.() -> Unit): FormPartAdapter {
        val adapter = FormPartAdapter(this, style)
        plusPart(adapter)
        adapter.create(block)
        return adapter
    }

    fun plusPart(part: FormPartAdapter) {
        adapter.addAdapter(part)
    }

    fun plusPart(
        index: Int,
        style: Style = NoneStyle,
        block: PartData.() -> Unit
    ): FormPartAdapter {
        val adapter = FormPartAdapter(this, style).apply {
            create(block)
        }
        plusPart(index, adapter)
        return adapter
    }

    fun plusPart(index: Int, part: FormPartAdapter) {
        adapter.addAdapter(index, part)
    }

    fun removePart(part: FormPartAdapter) {
        adapter.removeAdapter(part)
    }

    fun updateForm() {
        adapter.adapters.forEach {
            if (it is FormPartAdapter) it.update()
        }
    }

    fun partIndexOf(part: FormPartAdapter) = adapter.adapters.indexOf(part)

    fun partSize() = adapter.adapters.size

    private val stylePool = ArraySet<Style>()
    private val typesetPool = ArraySet<Typeset>()
    private val itemProviderPool = ArraySet<BaseFormProvider>()
    private val itemTypePool = ArraySet<Triple<Int, Int, Int>>()

    internal fun getItemViewType(style: Style, typeset: Typeset, provider: BaseFormProvider): Int {
        val type = Triple(
            stylePool.add(style).let { stylePool.indexOf(style) },
            typesetPool.add(typeset).let { typesetPool.indexOf(typeset) },
            itemProviderPool.add(provider).let { this.itemProviderPool.indexOf(provider) }
        )
        itemTypePool.add(type)
        return itemTypePool.indexOf(type)

    }

    internal fun getTypesetForItemViewType(viewType: Int): Typeset {
        val triple = itemTypePool.valueAt(viewType) ?: throw RuntimeException("No such viewType")
        return typesetPool.valueAt(triple.second) ?: throw RuntimeException("No such typeset")
    }

    internal fun getItemProviderForItemViewType(viewType: Int): BaseFormProvider {
        val triple = itemTypePool.valueAt(viewType) ?: throw RuntimeException("No such viewType")
        return itemProviderPool.valueAt(triple.third)
            ?: throw RuntimeException("No such itemProvider")
    }
}