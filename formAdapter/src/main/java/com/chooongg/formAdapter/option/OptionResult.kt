package com.chooongg.formAdapter.option

sealed class OptionResult<T> {

    /**
     * 空结果
     */
    class Empty<T> : OptionResult<T>()

    /**
     * 成功结果
     */
    class Success<T>(val options: List<T>) : OptionResult<T>()

    /**
     * 错误结果
     */
    class Error<T>(val e: Exception) : OptionResult<T>()
}

