package com.chooongg.formAdapter

import android.content.res.Resources
import com.chooongg.formAdapter.annotation.ContentGravity
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.format.DefaultNameFormat
import com.chooongg.formAdapter.format.FormNameFormat

object FormManager {

    var emsSize = 5
    var maxItemWidth = (300 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    var horizontalDividerType = Boundary.LOCAL
    var singleLineDividerType = Boundary.GLOBAL

    @ContentGravity
    var contentGravity = android.view.Gravity.NO_GRAVITY

    @ContentGravity
    var multiColumnContentGravity = android.view.Gravity.NO_GRAVITY

    var nameFormat: FormNameFormat = DefaultNameFormat

}