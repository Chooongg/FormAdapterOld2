package com.chooongg.formAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.FormGroupTitle

class FormAdapter : RecyclerView.Adapter<FormViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = asyncDiffer.currentList.size

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}