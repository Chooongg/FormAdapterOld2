package com.chooongg.formAdapter.item

import androidx.annotation.RestrictTo
import androidx.annotation.StringRes
import com.chooongg.formAdapter.option.BaseOption
import org.json.JSONArray
import org.json.JSONObject

abstract class BaseMultipleOptionForm<T>(
    @StringRes nameRes: Int?, name: CharSequence?, field: String?
) : BaseOptionForm<T>(nameRes, name, field){

    var selected: ArrayList<BaseOption>? = null

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    override var content: Any? = null

    fun checkedOption(option: BaseOption, isChecked: Boolean) {
        if (isChecked) {
            if (selected == null) selected = ArrayList()
            selected!!.add(option)
        } else {
            if (selected != null) {
                selected!!.remove(option)
                if (selected!!.isEmpty()) {
                    selected = null
                }
            }
        }
    }

    override fun outputData(json: JSONObject) {
        if (field != null && !selected.isNullOrEmpty()) json.put(field!!, JSONArray().apply {
            selected!!.forEach { put(it.getValue()) }
        })
        snapshotExtensionFieldAndContent().forEach {
            json.put(it.key, it.value)
        }
    }
}