package com.chooongg.formAdapter

import android.content.res.Resources
import com.chooongg.formAdapter.annotation.ContentGravity
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.format.DefaultNameFormat
import com.chooongg.formAdapter.format.FormNameFormat
import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.output.BaseItemDataProvider

object FormManager {

    private val formItemDataProviders = HashSet<BaseItemDataProvider>()

    var emsSize = 5
    var maxItemWidth = (300 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    var horizontalDividerType = Boundary.LOCAL
    var singleLineDividerType = Boundary.GLOBAL

    @ContentGravity
    var contentGravity = android.view.Gravity.NO_GRAVITY

    @ContentGravity
    var multiColumnContentGravity = android.view.Gravity.NO_GRAVITY

    var nameFormat: FormNameFormat = DefaultNameFormat

    fun putItemDataProvider(provider: BaseItemDataProvider) {
        formItemDataProviders.removeIf { it.getTargetClass() == provider.getTargetClass() }
        formItemDataProviders.add(provider)
    }

    internal fun getItemDataProvider(clazz: Class<out BaseForm>): BaseItemDataProvider? {
        return formItemDataProviders.find { it.getTargetClass() == clazz }
    }
}