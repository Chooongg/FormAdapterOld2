package com.chooongg.formAdapter.mode

/**
 * 可见模式
 */
enum class FormVisibilityMode {
    /**
     * 总是可见
     */
    ALWAYS,

    /**
     * 仅只读时可见
     */
    ONLY_SEE,

    /**
     * 仅编辑时可见
     */
    ONLY_EDIT,

    /**
     * 总不可见
     */
    NEVER
}