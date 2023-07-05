package com.chooongg.formAdapter.option

/**
 * OptionForm 的选项
 */
interface BaseOption {
    fun getName(): String
    fun getSecondaryName():String?
    fun getValue(): String?
}