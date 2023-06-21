package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.item.SingleLineForm

open class GroupData {

    val items = mutableListOf<BaseForm>()

    var groupName: CharSequence? = null

    internal var groupTitleItem: InternalFormGroupTitle? = null

    /**
     * 添加表单项
     */
    fun add(item: BaseForm) = items.add(item)

    /**
     * 添加单行表单项
     */
    fun addSingleLine(block: SingleLineCreator.() -> Unit) {
        val creator = SingleLineCreator().apply(block)
        items.add(SingleLineForm().apply {
            isShowOnEdge = creator.isShowOnEdge
            creator.items.forEach {
                if (it is SingleLineForm) throw IllegalArgumentException("GroupForm can not be added to SingleLine")
                it.isSingleLineItem = true
                it.typeset = creator.typeset
                it.enableMode = creator.enableMode
                it.visibilityMode = creator.visibilityMode
                it.isShowOnEdge = creator.isShowOnEdge
            }
            items = ArrayList(creator.items)
        })
    }

    /**
     * 清空表单项
     */
    fun clearItems() = items.clear()

    internal fun getGroupTitleItem(
        part: PartData,
        index: Int
    ): InternalFormGroupTitle? {
        val name = if (part.dynamicPart) {
            if (part.dynamicPartShowName) {
                part.dynamicPartNameFormatBlock?.invoke(part.dynamicPartName, index)
                    ?: "${part.dynamicPartName ?: ""}${index + 1}"
            } else null
        } else groupName
        groupTitleItem = if (name != null) {
            (groupTitleItem ?: InternalFormGroupTitle(name)).also {
                it.name = name
            }
        } else null
        return groupTitleItem
    }
}