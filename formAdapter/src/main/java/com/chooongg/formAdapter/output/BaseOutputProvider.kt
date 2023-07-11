package com.chooongg.formAdapter.output

import com.chooongg.formAdapter.item.BaseForm
import org.json.JSONObject

abstract class BaseOutputProvider {

    abstract fun getTargetClass(): Class<out BaseForm>

    abstract fun output(item: BaseForm, json: JSONObject)

}