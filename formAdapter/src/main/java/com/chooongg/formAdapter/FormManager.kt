package com.chooongg.formAdapter

import android.content.res.Resources
import com.chooongg.formAdapter.boundary.Boundary

object FormManager {

    var emsSize = 6
    var contentGravity = android.view.Gravity.NO_GRAVITY
    var multiColumnContentGravity = android.view.Gravity.NO_GRAVITY
    var maxItemWidth = dp2px(300f)
    var horizontalDividerType = Boundary.LOCAL
    var singleLineDividerType = Boundary.GLOBAL

    fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}