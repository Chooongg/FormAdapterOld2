package com.chooongg.formAdapter.item

import androidx.annotation.IntDef
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.enum.FormOptionLoadMode
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.option.OptionResult
import kotlinx.coroutines.launch

/**
 * 选项表单 Item 基类
 */
abstract class BaseOptionForm(name: CharSequence?, field: String?) : BaseForm(name, field) {

    companion object {
        const val OPTION_LOAD_TYPE_CREATE = 0
        const val OPTION_LOAD_TYPE_OPEN = 1
    }

    @IntDef(OPTION_LOAD_TYPE_CREATE, OPTION_LOAD_TYPE_OPEN)
    annotation class OptionLoadType

    /**
     * 是否具有打开操作
     */
    abstract fun hasOpenOperation(): Boolean

    private var localOptions: List<BaseOption>? = null

    /**
     * 选项加载模式
     */
    open var optionLoadMode = FormOptionLoadMode.OPEN_AND_EMPTY

    private var optionLoader: (suspend (BaseOptionForm) -> OptionResult)? = null

    protected var optionResult: OptionResult = OptionResult.NotLoading

    val options: List<BaseOption>?
        get() = localOptions ?: (optionResult as? OptionResult.Success)?.options

    fun localOptions(local: List<BaseOption>) {
        localOptions = local
    }

    fun isNeedToLoadOption(adapter: FormPartAdapter, @OptionLoadType type: Int) =
        if (localOptions != null) {
            false
        } else if (adapter.formAdapter.isEditable) {
            if (type == OPTION_LOAD_TYPE_CREATE) {
                when (optionLoadMode) {
                    FormOptionLoadMode.ALWAYS -> true
                    FormOptionLoadMode.EMPTY -> options.isNullOrEmpty()
                    FormOptionLoadMode.OPEN_AND_EMPTY -> !hasOpenOperation() && options.isNullOrEmpty()
                    FormOptionLoadMode.OPEN -> !hasOpenOperation()
                    else -> false
                }
            } else {
                when (optionLoadMode) {
                    FormOptionLoadMode.ALWAYS -> true
                    FormOptionLoadMode.OPEN_AND_EMPTY -> options.isNullOrEmpty()
                    FormOptionLoadMode.OPEN -> true
                    else -> false
                }
            }
        } else if (type == OPTION_LOAD_TYPE_CREATE) {
            when (optionLoadMode) {
                FormOptionLoadMode.ALWAYS -> true
                FormOptionLoadMode.EMPTY -> options.isNullOrEmpty()
                FormOptionLoadMode.OPEN_AND_EMPTY -> options.isNullOrEmpty()
                FormOptionLoadMode.OPEN -> true
                else -> false
            }
        } else false

    open fun loadOption(adapter: FormPartAdapter, notifyUpdate: () -> Unit) {
        adapter.adapterScope.launch {
            if (optionLoader == null) {
                optionResult = OptionResult.NotLoading
                notifyUpdate.invoke()
                return@launch
            }
            optionResult = OptionResult.Loading
            notifyUpdate.invoke()
            optionResult = optionLoader!!.invoke(this@BaseOptionForm)
            notifyUpdate.invoke()
        }
    }
}