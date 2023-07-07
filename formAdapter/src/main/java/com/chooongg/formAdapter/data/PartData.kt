package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.FormPartNameFormatter

class PartData {

    val groups = mutableListOf<GroupData>()

    /**
     * 是否启用片段
     */
    var isEnablePart: Boolean = true

    /**
     * 片段字段
     */
    var partField: String? = null

    /**
     * 是否是动态片段
     */
    val dynamicPart get() = dynamicPartCreateGroupBlock != null

    /**
     * 片段名称
     */
    var dynamicPartName: CharSequence? = null

    internal var dynamicPartCreateGroupBlock: (GroupData.() -> Unit)? = null

    internal var dynamicPartNameFormatBlock: FormPartNameFormatter? = null

    /**
     * 动态片段显示名称
     */
    var dynamicPartShowName = true

    /**
     * 动态片段最小组数量
     */
    @androidx.annotation.IntRange(from = 0)
    var dynamicPartMinGroupCount: Int = 1

    /**
     * 动态片段最大组数量
     */
    @androidx.annotation.IntRange(from = 1)
    var dynamicPartMaxGroupCount: Int = Int.MAX_VALUE

    /**
     * 动态片段添加组
     */
    fun dynamicPartCreateGroupListener(block: (GroupData.() -> Unit)?) {
        dynamicPartCreateGroupBlock = block
    }

    /**
     * 动态片段名称格式化
     */
    fun dynamicPartNameFormatListener(block: FormPartNameFormatter?) {
        dynamicPartNameFormatBlock = block
    }

    /**
     * 添加组
     */
    fun plusGroup(block: GroupData.() -> Unit) {
        groups.add(GroupData().apply(block))
    }

    /**
     * 添加组
     */
    fun plusGroup(group: GroupData) {
        groups.add(group)
    }

    /**
     * 删除组
     */
    fun removeGroup(group: GroupData) {
        groups.remove(group)
    }

    /**
     * 清空组
     */
    fun clearGroups() = groups.clear()
}