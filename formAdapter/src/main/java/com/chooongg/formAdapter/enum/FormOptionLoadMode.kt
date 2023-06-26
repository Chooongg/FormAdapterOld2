package com.chooongg.formAdapter.enum

/**
 * 选项加载模式
 */
enum class FormOptionLoadMode {
    /**
     * 无
     */
    NONE,

    /**
     * 为空时
     */
    EMPTY,

    /**
     * 打开并且为空时
     */
    OPEN_AND_EMPTY,

    /**
     * 每次打开时
     */
    OPEN,

    /**
     * 总是
     */
    ALWAYS,
}