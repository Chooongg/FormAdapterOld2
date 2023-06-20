package com.chooongg.formAdapter

import android.content.res.Resources

object FormManager {
    var emsSize = 7
    var contentGravity = android.view.Gravity.NO_GRAVITY
    var maxItemWidth = (300f * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}