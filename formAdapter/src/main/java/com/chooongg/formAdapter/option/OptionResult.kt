package com.chooongg.formAdapter.option

sealed class OptionResult {
    object NotLoading : OptionResult()
    object Loading : OptionResult()
    class Success(val options: List<BaseOption>) : OptionResult()
    class Error(e: Exception) : OptionResult()
}