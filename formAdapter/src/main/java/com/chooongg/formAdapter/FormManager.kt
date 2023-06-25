package com.chooongg.formAdapter

import android.content.res.Resources

object FormManager {

    var emsSize = 7
    var contentGravity = android.view.Gravity.NO_GRAVITY
    var multiColumnContentGravity = android.view.Gravity.NO_GRAVITY
    var maxItemWidth = dp2px(300f)

    fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}