package com.chooongg.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.data.PartData
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle
import com.chooongg.formAdapter.style.Style
import java.lang.ref.WeakReference

class FormPartAdapter(
    val helper: FormHelper,
    private val style: Style
) : RecyclerView.Adapter<FormViewHolder>() {

    private var _recyclerView: WeakReference<RecyclerView>? = null

    val recyclerView get() = _recyclerView?.get()

    private val asyncDiffer =
        AsyncListDiffer(object : ListUpdateCallback {
            override fun onChanged(position: Int, count: Int, payload: Any?) =
                notifyItemChanged(position, payload)

            override fun onRemoved(position: Int, count: Int) =
                notifyItemRangeRemoved(position, count)

            override fun onInserted(position: Int, count: Int) =
                notifyItemRangeInserted(position, count)

            override fun onMoved(fromPosition: Int, toPosition: Int) =
                notifyItemMoved(fromPosition, toPosition)

        }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm>() {
            override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =when{
                oldItem is FormGroupTitle -> false
                newItem is FormGroupTitle -> false
                else-> true
            }

            override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) =
                oldItem.antiRepeatCode == newItem.antiRepeatCode
        }).build())

    var isEnable = true
        set(value) {
            field = value
            update()
        }

    var data = PartData()

    fun create(data: PartData.() -> Unit) {
        create(PartData().apply(data))
    }

    fun create(data: PartData) {
        this.data = data
        update()
    }

    fun update() {
        if (!isEnable) {
            asyncDiffer.submitList(null)
            return
        }
        // 去除空组
        data.groups.forEach { if (it.items.isEmpty()) data.groups.remove(it) }
        data.groups.forEachIndexed { index, group ->
            group.items.forEach {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val styleLayout = style.onCreateStyleLayout(parent)
        val typesetLayout = helper.getTypesetForItemViewType(viewType)
            .onCreateTypesetLayout(styleLayout ?: parent)
        val itemView = helper.getItemProviderForItemViewType(viewType)
            .onCreateItemView(typesetLayout ?: styleLayout ?: parent)
        return FormViewHolder(styleLayout ?: typesetLayout ?: itemView)
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = asyncDiffer.currentList[position]
        style.onBindStyleLayout(holder, item)
        helper.getTypesetForItemViewType(holder.itemViewType).onBindTypesetLayout(holder, item)
        helper.getItemProviderForItemViewType(holder.itemViewType)
            .onBindItemView(helper, holder, item)
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = asyncDiffer.currentList[position]
        style.onBindStyleLayout(holder, item)
        helper.getTypesetForItemViewType(holder.itemViewType).onBindTypesetLayout(holder, item)
        helper.getItemProviderForItemViewType(holder.itemViewType)
            .onBindItemView(helper, holder, item, payloads)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = null
    }

    override fun getItemViewType(position: Int): Int {
        val item = asyncDiffer.currentList[position]
        return helper.getItemViewType(
            style, item.typeset ?: style.defaultTypeset, item.getItemProvider(helper)
        )
    }
}