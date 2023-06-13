package com.chooongg.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.data.PartCreator
import com.chooongg.formAdapter.data.PartData
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle
import com.chooongg.formAdapter.style.Style
import java.lang.ref.WeakReference

class FormPartAdapter(
    val style: Style
) : RecyclerView.Adapter<FormViewHolder>() {

    private var _formView: WeakReference<FormView>? = null

    val formView get() = _formView?.get()

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
            override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
                if (oldItem is FormGroupTitle || newItem is FormGroupTitle) false else oldItem == newItem

            override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) =
                oldItem.antiRepeatCode == newItem.antiRepeatCode
        }).build())

    var data: PartData = PartData()
        private set

    var isShow = true
        set(value) {
            field = value
            update()
        }

    fun submit(creator: PartCreator.() -> Unit) {
        submit(PartCreator().apply(creator))
    }

    fun submit(creator: PartCreator) {
        this.data = PartData(creator)
        update()
    }

    fun update() {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView is FormView) _formView = WeakReference(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _formView = null
    }
}