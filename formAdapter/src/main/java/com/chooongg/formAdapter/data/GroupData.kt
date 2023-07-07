package com.chooongg.formAdapter.data

import com.chooongg.formAdapter.item.BaseForm
import com.chooongg.formAdapter.item.InternalFormGroupTitle
import com.chooongg.formAdapter.item.SingleLineForm

open class GroupData : AbstractMenuFormData(), FormCreator {

    private val _items = mutableListOf<BaseForm>()

    internal var groupTitleItem: InternalFormGroupTitle? = null

    override fun getItems(): MutableList<BaseForm> = _items

    /**
     * 添加单行表单项
     */
    fun addSingleLine(block: SingleLineCreator.() -> Unit) {
        val creator = SingleLineCreator().apply(block)
        add(SingleLineForm().apply {
            creator.getItems().forEach {
                if (it is SingleLineForm) throw IllegalArgumentException("GroupForm can not be added to SingleLine")
                it.isSingleLineItem = true
            }
            items = ArrayList(creator.getItems())
        })
    }

    /**
     * 清空表单项
     */
    fun clearItems() = _items.clear()

    internal fun getGroupTitleItem(
        part: PartData,
        index: Int
    ): InternalFormGroupTitle? {
        groupTitleItem = if ((part.dynamicPart && part.dynamicPartShowName) || name != null) {
            (groupTitleItem ?: InternalFormGroupTitle(nameRes, name)).also {
                it.isDynamicPart = part.dynamicPart
                it.nameRes = nameRes
                it.name = if (part.dynamicPart) {
                    if (part.dynamicPartShowName) {
                        part.dynamicPartNameFormatBlock?.invoke(part.dynamicPartName, index)
                    } else null
                } else name
                it.menuTextRes = menuTextRes
                it.menuText = menuText
                it.menuIconRes = menuIconRes
                it.menuVisibilityMode = menuVisibilityMode
                it.menuEnableMode = menuEnableMode
            }
        } else null
        return groupTitleItem
    }
}