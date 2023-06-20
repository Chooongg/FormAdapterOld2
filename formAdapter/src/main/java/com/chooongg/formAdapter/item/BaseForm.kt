package com.chooongg.formAdapter.item

import androidx.annotation.DrawableRes
import androidx.annotation.Px
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormOutputMode
import com.chooongg.formAdapter.enum.FormVisibilityMode
import com.chooongg.formAdapter.provider.BaseFormProvider
import com.chooongg.formAdapter.typeset.Typeset
import org.json.JSONObject
import kotlin.random.Random

abstract class BaseForm(
    /**
     * 名称
     */
    var name: CharSequence?,
    /**
     * 字段
     */
    val field: String?,
) {

    //<editor-fold desc="基础 Basic">

    /**
     * 可根据 adapter 中的信息动态配置视图代理，建议非必要除本身代理和 Text 代理外不要使用其他代理
     */
    abstract fun getItemProvider(adapter: FormAdapter): BaseFormProvider

    /**
     * 提示
     */
    open var hint: CharSequence? = null

    /**
     * 内容
     */
    open var content: Any? = null

    /**
     * 是否必填
     */
    open var isRequired: Boolean = false

    /**
     * 可见模式
     */
    open var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 启用模式
     */
    open var enableMode: FormEnableMode = FormEnableMode.ONLY_EDIT

    /**
     * 输出模式
     */
    open var outputMode: FormOutputMode = FormOutputMode.ALWAYS

    /**
     * 是否在组边缘展示
     */
    open var isShowOnEdge = true

    //</editor-fold>

    //<editor-fold desc="菜单 Menu">

    /**
     * 菜单文本
     */
    open var menuText: CharSequence? = null

    /**
     * 菜单图标
     */
    @DrawableRes
    open var menuIconRes: Int? = null

    /**
     * 菜单图标大小
     */
    @Px
    open var menuIconSize: Int? = null

    /**
     * 菜单可见模式
     */
    open var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 菜单启用模式
     */
    open var menuEnableMode: FormEnableMode = FormEnableMode.ONLY_EDIT

    //</editor-fold>

    //<editor-fold desc="排版 Typeset">

    /**
     * 自定义排版样式
     */
    open var typeset: Typeset? = null

    //</editor-fold>

    //<editor-fold desc="内部 internal">

    /**
     * 边界信息
     */
    var marginBoundary: Boundary = Boundary()
        internal set

    var paddingBoundary: Boundary = Boundary()
        internal set

    /**
     * 组索引
     */
    var groupIndex = -1
        internal set

    /**
     * 组中的位置
     */
    var positionForGroup = -1
        internal set

    /**
     * 反重复代码
     */
    internal val antiRepeatCode = System.currentTimeMillis() + Random.nextLong(10000)

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, Any?>? = null

    /**
     * 自定义输出接口
     */
    private var customOutputBlock: ((json: JSONObject) -> Unit)? = null

    //</editor-fold>

    //<editor-fold desc="扩展 extend">

    /**
     * 放置扩展内容
     */
    fun putExtensionContent(key: String, value: Any?) {
        if (value != null) {
            if (extensionFieldAndContent == null) extensionFieldAndContent = HashMap()
            extensionFieldAndContent!![key] = value
        } else if (extensionFieldAndContent != null) {
            extensionFieldAndContent!!.remove(key)
            if (extensionFieldAndContent!!.isEmpty()) extensionFieldAndContent = null
        }
    }

    /**
     * 获取扩展内容
     */
    fun getExtensionContent(key: String): Any? = extensionFieldAndContent?.get(key)

    /**
     * 是否有扩展内容
     */
    fun hasExtensionContent(key: String) = extensionFieldAndContent?.containsKey(key) ?: false

    /**
     * 快照扩展字段和内容
     */
    fun snapshotExtensionFieldAndContent() = extensionFieldAndContent ?: emptyMap()

    /**
     * 删除扩展字段
     */
    fun removeExtensionContent(key: String) = extensionFieldAndContent?.remove(key)

    //</editor-fold>

    /**
     * 设置自定义输出监听
     */
    fun customOutput(block: ((json: JSONObject) -> Unit)?) {
        customOutputBlock = block
    }

    /**
     * 真实的可见性
     */
    open fun isRealVisible(adapter: FormAdapter): Boolean {
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_EDIT -> adapter.isEditable
            FormVisibilityMode.ONLY_SEE -> !adapter.isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 真实的可用性
     */
    open fun isRealEnable(adapter: FormAdapter): Boolean {
        return when (enableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ONLY_EDIT -> adapter.isEditable
            FormEnableMode.ONLY_SEE -> !adapter.isEditable
            FormEnableMode.NEVER -> false
        }
    }

    open fun isRealMenuVisible(isEditable: Boolean): Boolean {
        if (menuText == null && menuIconRes == null) return false
        return when (menuVisibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_EDIT -> isEditable
            FormVisibilityMode.ONLY_SEE -> !isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 检查数据正确性
     */
    open fun checkDataCorrectness(): Boolean {
        return if (isRequired) content != null else true
    }

    /**
     * 执行输出
     */
    fun executeOutput(adapter: FormAdapter, json: JSONObject) {
        val isRealVisible = isRealVisible(adapter)
        val isRealEnable = isRealEnable(adapter)
        if (outputMode == FormOutputMode.VISIBLE && !isRealVisible) return
        if (outputMode == FormOutputMode.VISIBLE_AND_ENABLED && !isRealVisible && !isRealEnable) return
        if (outputMode == FormOutputMode.ENABLED && !isRealEnable) return
        if (customOutputBlock != null) {
            customOutputBlock!!.invoke(json)
            return
        }
        outputData(json)
    }

    /**
     * 输出处理
     */
    protected open fun outputData(json: JSONObject) {
        json.putOpt(field, content)
        extensionFieldAndContent?.forEach {
            json.put(it.key, it.value)
        }
    }

    /**
     * 获取内容文本
     */
    open fun getContentText(): CharSequence? = content?.toString()
}