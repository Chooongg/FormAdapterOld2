package com.chooongg.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.chooongg.formAdapter.data.PartData
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle
import com.chooongg.formAdapter.style.NoneStyle
import com.chooongg.formAdapter.style.Style
import java.lang.ref.WeakReference

class FormPartAdapter internal constructor(
    val helper: FormHelper,
    val style: Style = NoneStyle
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
            override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) = when {
                oldItem is FormGroupTitle -> false
                newItem is FormGroupTitle -> false
                else -> true
            }

            override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) =
                oldItem.antiRepeatCode == newItem.antiRepeatCode
        }).build())

    var isEnablePart = true
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
        if (!data.isEnablePart) {
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
        style.createMarginInfo(parent.context)
        val styleLayout = style.onCreateStyleLayout(parent)
        val typeset = helper.getTypesetForItemViewType(viewType)
        val typesetLayout = typeset.onCreateTypesetLayout(styleLayout ?: parent)
        val itemView = helper.getItemProviderForItemViewType(viewType)
            .onCreateItemView(this, typeset, typesetLayout ?: styleLayout ?: parent)
        return FormViewHolder(styleLayout ?: typesetLayout ?: itemView)
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = asyncDiffer.currentList[position]
        style.onBindStyleLayout(holder, item)
        val typeset = helper.getTypesetForItemViewType(holder.itemViewType)
        typeset.onBindTypesetLayout(holder, item)
        helper.getItemProviderForItemViewType(holder.itemViewType)
            .onBindItemView(this, typeset, holder, item)
    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = asyncDiffer.currentList[position]
        style.onBindStyleLayout(holder, item)
        val typeset = helper.getTypesetForItemViewType(holder.itemViewType)
        typeset.onBindTypesetLayout(holder, item)
        helper.getItemProviderForItemViewType(holder.itemViewType)
            .onBindItemView(this, typeset, holder, item, payloads)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        helper.getItemProviderForItemViewType(holder.itemViewType).onItemRecycler(holder)
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