package com.chooongg.formAdapter.item

import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormOptionLoader
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.enum.FormOptionLoadMode
import com.chooongg.formAdapter.option.BaseOption
import com.chooongg.formAdapter.option.OptionResult
import com.chooongg.formAdapter.option.OptionState
import com.chooongg.utils.ext.style
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 选项表单 Item 基类
 */
abstract class BaseOptionForm<T>(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

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

    var optionResult: OptionResult<T> = OptionState.Wait()
        protected set

    val options: List<T>?
        get() = localOptions ?: (optionResult as? OptionResult.Success<T>)?.options

    fun localOptions(local: List<T>) {
        localOptions = local
    }

    fun isNeedToLoadOption(holder: FormViewHolder): Boolean {
        if (localOptions != null) return false
        if (optionLoader == null) return false
        if (optionResult is OptionState.Loading<T>) return false
        if (holder.job?.isActive == true) return false
        return when (optionLoadMode) {
            FormOptionLoadMode.ALWAYS -> true
            FormOptionLoadMode.EMPTY -> options.isNullOrEmpty()
        }
    }

    open fun loadOption(
        adapter: FormPartAdapter,
        holder: FormViewHolder,
        notifyUpdate: () -> Unit
    ) {
        if (optionLoader == null) {
            optionResult = OptionState.Wait()
            notifyUpdate.invoke()
            return
        }
        holder.job = adapter.adapterScope.launch {
            try {
                optionResult = OptionState.Loading()
                withContext(Dispatchers.Main) { notifyUpdate.invoke() }
                val result = optionLoader!!.invoke(this@BaseOptionForm)
                optionResult = if (result.isNullOrEmpty()) {
                    OptionResult.Empty()
                } else OptionResult.Success(result)
                withContext(Dispatchers.Main) { notifyUpdate.invoke() }
            } catch (e: CancellationException) {
                optionResult = OptionState.Wait()
                holder.job = null
            } catch (e: Exception) {
                optionResult = OptionResult.Error(e)
                withContext(Dispatchers.Main) { notifyUpdate.invoke() }
            }
        }
    }

    fun optionLoader(block: FormOptionLoader<T>?) {
        optionLoader = block
    }

    override fun getContentText(adapter: FormPartAdapter, holder: FormViewHolder): CharSequence? {
        if (content == null) return null
        val option = content as? BaseOption ?: return content?.toString()
        val text =
            option.getName().style {} + " ".style {} + (option.getSecondaryName() ?: "").style {
                setTextSizeRelative(0.8f)
            }
        return text.toSpannableString()
    }
}