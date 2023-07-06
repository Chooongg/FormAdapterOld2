package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormOptionLoader
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.enum.FormOptionLoadMode
import com.chooongg.formAdapter.option.OptionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 选项表单 Item 基类
 */
abstract class BaseOptionForm<T>(name: CharSequence?, field: String?) : BaseForm(name, field) {

    /**
     * 是否具有打开操作
     */
    abstract fun hasOpenOperation(): Boolean

    private var localOptions: List<T>? = null

    /**
     * 选项加载模式
     */
    open var optionLoadMode = FormOptionLoadMode.EMPTY

    /**
     * 选项加载器
     */
    private var optionLoader: FormOptionLoader<T>? = null

    var loaderResult: OptionResult<T> = OptionResult.NotLoading()
        protected set

    val options: List<T>?
        get() = localOptions ?: (loaderResult as? OptionResult.Success<T>)?.options

    fun localOptions(local: List<T>) {
        localOptions = local
    }

    fun isNeedToLoadOption() =
        if (localOptions != null) {
            false
        } else if (loaderResult is OptionResult.NotLoading || loaderResult is OptionResult.Error) {
            when (optionLoadMode) {
                FormOptionLoadMode.ALWAYS -> true
                FormOptionLoadMode.EMPTY -> options.isNullOrEmpty()
            }
        } else false

    open fun loadOption(adapter: FormPartAdapter, notifyUpdate: () -> Unit) {
        adapter.adapterScope.launch {
            if (optionLoader == null) {
                loaderResult = OptionResult.NotLoading()
                withContext(Dispatchers.Main) {
                    notifyUpdate.invoke()
                }
                return@launch
            }
            loaderResult = OptionResult.Loading()
            withContext(Dispatchers.Main) {
                notifyUpdate.invoke()
            }
            loaderResult = optionLoader!!.invoke(this@BaseOptionForm)
            withContext(Dispatchers.Main) {
                notifyUpdate.invoke()
            }
        }
    }

    fun optionLoader(block: FormOptionLoader<T>?) {
        optionLoader = block
    }
}