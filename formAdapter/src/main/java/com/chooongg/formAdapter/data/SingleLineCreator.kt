package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormVisibilityMode
import com.chooongg.formAdapter.typeset.Typeset

class SingleLineCreator : GroupData() {

    /**
     * 是否在组边缘展示
     */
    var isShowOnEdge = true

    /**
     * 自定义排版样式
     */
    var typeset: Typeset? = null

    /**
     * 可见模式
     */
    var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 启用模式
     */
    var enableMode: FormEnableMode = FormEnableMode.ONLY_EDIT

}