package com.chooongg.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.data.GroupData
import com.chooongg.formAdapter.data.PartData
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.SingleLineForm
import com.chooongg.formAdapter.style.NoneStyle
import com.chooongg.formAdapter.style.Style
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

class FormPartAdapter internal constructor(
    val formAdapter: FormAdapter, val style: Style = NoneStyle
) : RecyclerView.Adapter<FormViewHolder>() {

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        internal set

    private var _recyclerView: WeakReference<RecyclerView>? = null

    val recyclerView get() = _recyclerView?.get()

    private lateinit var defaultPartName: String

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeRemoved(position, count)

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)

    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            oldItem.javaClass == newItem.javaClass

        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            oldItem.antiRepeatCode == newItem.antiRepeatCode
    }).build())

    var data = PartData()

    fun create(data: PartData.() -> Unit) {
        create(PartData().apply(data))
    }

    fun create(data: PartData) {
        this.data = data
        update()
    }

    fun update() {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        if (!data.isEnablePart) {
            asyncDiffer.submitList(null)
            return
        }
        val tempList = mutableListOf<MutableList<BaseForm>>()
        data.groups.forEach { if (it.getItems().isEmpty()) data.groups.remove(it) } // 去除空组
        if (data.dynamicPart) {
            if (data.dynamicPartCreateGroupBlock != null) {
                while (data.groups.size < data.dynamicPartMinGroupCount) {
                    val groupData = GroupData()
                    data.dynamicPartCreateGroupBlock!!.invoke(groupData)
                    data.groups.add(groupData)
                }
            }
        }
        data.groups.forEachIndexed { groupIndex, group ->
            val groupList = mutableListOf<BaseForm>()
            group.getGroupTitleItem(data, groupIndex)?.also { groupList.add(it) }
            group@ for (item in group.getItems()) {
                if (item is SingleLineForm) {
                    val singleLines = ArrayList<BaseForm>()
                    singleLine@ for (it in item.items) {
                        it.globalPosition = -1
                        it.groupCount = -1
                        it.groupIndex = -1
                        it.itemCountForGroup = -1
                        it.positionForGroup = -1
                        it.nextItemIsSingleColumn = false
                        if (!it.isRealVisible(formAdapter)) continue@singleLine
                        singleLines.add(it)
                    }
                    disassemblySingleLine(singleLines)

                } else {
                    item.globalPosition = -1
                    item.groupCount = -1
                    item.groupIndex = -1
                    item.itemCountForGroup = -1
                    item.positionForGroup = -1
                    if (!item.isRealVisible(formAdapter)) continue@group
                    groupList.add(item)
                }
            }
            while (groupList.size > 0 && !groupList[0].isShowOnEdge) {
                groupList.removeAt(0)
            }
            while (groupList.size > 1 && !groupList[groupList.lastIndex].isShowOnEdge) {
                groupList.removeAt(groupList.lastIndex)
            }
            tempList.add(groupList)
        }
        tempList.forEachIndexed { index, group ->
            group.forEachIndexed { position, item ->
                item.groupCount = tempList.size
                item.groupIndex = index
                item.itemCountForGroup = group.size
                item.positionForGroup = position
                if (item.isMustSingleColumn && position > 0) {
                    group[position - 1].nextItemIsSingleColumn = true
                }
            }
        }
        asyncDiffer.submitList(ArrayList<BaseForm>().apply { tempList.forEach { addAll(it) } }) {
            notifyItemRangeChanged(0, itemCount)
        }
    }

    private fun disassemblySingleLine(singleLines: ArrayList<BaseForm>) {
        var maxWeight = 0f
        singleLines.forEachIndexed { index, it ->
            it.singleLineCount = singleLines.size
            it.singleLineIndex = index
            maxWeight += it.singleLineWeight
        }
        if (singleLines.size == 1) singleLines[0].itemSpan = 2520
        else {
            var spanCount = 0
            singleLines.forEach {
                it.itemSpan = (2520 / maxWeight * it.singleLineWeight).toInt()
                spanCount += it.itemSpan
            }
            var index = 0
            while (spanCount < 2520) {
                singleLines[index].itemSpan++
                if (index + 1 == singleLines.size) index = 0 else index++
                spanCount++
            }
        }
    }

    fun findOfField(
        field: String, update: Boolean = true, block: (BaseForm) -> Unit
    ): Boolean {
        data.groups.forEach { group ->
            group.getItems().forEach {
                if (it.field == field) {
                    block(it)
                    return true
                }
            }
        }
        asyncDiffer.currentList.forEachIndexed { index, item ->
            if (item.field == field) {
                block(item)
                if (update) update()
                return true
            }
        }
        return false
    }

    fun getItemList() = asyncDiffer.currentList

    override fun getItemCount() = asyncDiffer.currentList.size

    fun getItem(position: Int) = asyncDiffer.currentList[position]

    override fun getItemViewType(position: Int): Int {
        val item = asyncDiffer.currentList[position]
        return formAdapter.getItemViewType(
            style, item.typeset ?: style.defaultTypeset, item.getItemProvider(formAdapter)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val styleLayout = style.onCreateStyleLayout(parent)
        val typeset = formAdapter.getTypesetForItemViewType(viewType)
        val typesetLayout = typeset.onCreateTypesetLayout(styleLayout ?: parent, style.paddingInfo)
        val itemView = formAdapter.getItemProviderForItemViewType(viewType)
            .onCreateItemView(this, typeset, typesetLayout ?: styleLayout ?: parent)
        if (typesetLayout != null) {
            styleLayout?.addView(typesetLayout)
            typesetLayout.addView(itemView)
        } else {
            styleLayout?.addView(itemView)
        }
        return FormViewHolder(styleLayout ?: typesetLayout ?: itemView)
    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = asyncDiffer.currentList[position]
        style.onBindStyleLayout(holder, item)
        val typeset = formAdapter.getTypesetForItemViewType(holder.itemViewType)
        typeset.onBindTypesetLayout(this, holder, item)
        formAdapter.getItemProviderForItemViewType(holder.itemViewType)
            .onBindItemView(this, typeset, holder, item)
    }

    override fun onBindViewHolder(
        holder: FormViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        val item = asyncDiffer.currentList[position]
        style.onBindStyleLayout(holder, item)
        val typeset = formAdapter.getTypesetForItemViewType(holder.itemViewType)
        typeset.onBindTypesetLayout(this, holder, item)
        formAdapter.getItemProviderForItemViewType(holder.itemViewType)
            .onBindItemView(this, typeset, holder, item, payloads)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        formAdapter.getItemProviderForItemViewType(holder.itemViewType).onItemRecycler(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        defaultPartName = recyclerView.resources.getString(R.string.formDefaultGroupName)
        style.createMarginAndPaddingInfo(recyclerView.context)
        _recyclerView = WeakReference(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = null
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }
}