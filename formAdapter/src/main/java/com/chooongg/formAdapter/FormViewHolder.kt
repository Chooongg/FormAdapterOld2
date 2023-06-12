package com.chooongg.formAdapter

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

class FormViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val views: SparseArray<View> = SparseArray()

//    val paddingVerticalGlobal =
//        view.resources.getDimensionPixelOffset(R.dimen.formVerticalGlobalPaddingSize)
//    val paddingVerticalLocal =
//        view.resources.getDimensionPixelOffset(R.dimen.formVerticalLocalPaddingSize)
//    val paddingHorizontalGlobal =
//        view.resources.getDimensionPixelOffset(R.dimen.formHorizontalGlobalPaddingSize)
//    val paddingHorizontalLocal =
//        view.resources.getDimensionPixelOffset(R.dimen.formHorizontalLocalPaddingSize)

    fun <T : View> getView(@IdRes viewId: Int): T {
        val view = getViewOrNull<T>(viewId)
        checkNotNull(view) { "No view found with id $viewId" }
        return view
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getViewOrNull(@IdRes viewId: Int): T? {
        val view = views.get(viewId)
        if (view == null) {
            itemView.findViewById<T>(viewId)?.let {
                views.put(viewId, it)
                return it
            }
        }
        return view as? T
    }
}