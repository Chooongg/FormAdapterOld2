package com.chooongg.formAdapter.option

sealed class OptionResult<T> {
    class NotLoading<T> : OptionResult<T>()
    class Loading<T> : OptionResult<T>()
    class Success<T>(val options: List<T>) : OptionResult<T>()
    class Error<T>(e: Exception) : OptionResult<T>()
}