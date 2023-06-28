package com.chooongg.formAdapter

import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.format.DefaultNameFormat
import com.chooongg.formAdapter.format.FormNameFormat
import com.chooongg.utils.ext.dp2px

object FormManager {

    var emsSize = 6
    var contentGravity = android.view.Gravity.NO_GRAVITY
    var multiColumnContentGravity = android.view.Gravity.NO_GRAVITY
    var maxItemWidth = dp2px(300f)
    var horizontalDividerType = Boundary.LOCAL
    var singleLineDividerType = Boundary.GLOBAL

    var nameFormat: FormNameFormat = DefaultNameFormat

}