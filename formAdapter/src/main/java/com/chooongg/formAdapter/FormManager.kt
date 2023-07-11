package com.chooongg.formAdapter

import android.content.res.Resources
import com.chooongg.formAdapter.annotation.ContentGravity
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.format.DefaultNameFormat
import com.chooongg.formAdapter.format.FormNameFormat
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.output.BaseOutputProvider

object FormManager {

    private val outputProviders = HashSet<BaseOutputProvider>()

    var emsSize = 5
    var maxItemWidth = (300 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    var horizontalDividerType = Boundary.LOCAL
    var singleLineDividerType = Boundary.GLOBAL

    @ContentGravity
    var contentGravity = android.view.Gravity.NO_GRAVITY

    @ContentGravity
    var multiColumnContentGravity = android.view.Gravity.NO_GRAVITY

    var nameFormat: FormNameFormat = DefaultNameFormat

    fun putOutputProvider(provider: BaseOutputProvider) {
        outputProviders.removeIf { it.getTargetClass() == provider.getTargetClass() }
        outputProviders.add(provider)
    }

    internal fun getOutputProvider(clazz: Class<out BaseForm>): BaseOutputProvider? {
        return outputProviders.find { it.getTargetClass() == clazz }
    }
}