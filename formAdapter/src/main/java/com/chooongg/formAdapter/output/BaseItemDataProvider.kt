package com.chooongg.formAdapter.output

import com.chooongg.formAdapter.FormDataVerificationException
import com.chooongg.formAdapter.item.BaseForm
import org.json.JSONObject

abstract class BaseItemDataProvider {

    abstract fun getTargetClass(): Class<out BaseForm>

    @Throws(FormDataVerificationException::class)
    abstract fun dataCorrectness(item: BaseForm)

    abstract fun output(item: BaseForm, json: JSONObject)

}