package com.chooongg.formAdapter.option

sealed class OptionState<T> : OptionResult<T>() {
    /**
     * 等待状态
     */
    class Wait<T> : OptionState<T>()
    class Loading<T> : OptionState<T>()
}