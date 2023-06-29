package com.chooongg.formAdapter.item

import androidx.annotation.GravityInt
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormLinkageBlock
import com.chooongg.formAdapter.boundary.Boundary
import com.chooongg.formAdapter.data.AbstractMenuFormData
import com.chooongg.formAdapter.enum.FormOutputMode
import com.chooongg.formAdapter.provider.BaseFormProvider
import com.chooongg.formAdapter.typeset.Typeset
import org.json.JSONObject
import kotlin.random.Random

/**
 * 表单 Item 基类
 */
abstract class BaseForm(
    /**
     * 名称
     */
    name: CharSequence?,
    /**
     * 字段
     */
    val field: String?,
) : AbstractMenuFormData(name) {

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
    open var isMust: Boolean = false

    /**
     * 输出模式
     */
    open var outputMode: FormOutputMode = FormOutputMode.ALWAYS

    /**
     * 是否在组边缘展示
     */
    open var isShowOnEdge = true

    /**
     * 是否强制单列, 多列模块下无效
     */
    open var isMustSingleColumn: Boolean = false


    //</editor-fold>

    //<editor-fold desc="菜单 Menu">

    //</editor-fold>

    //<editor-fold desc="排版 Typeset">

    /**
     * 自定义排版样式
     */
    open var typeset: Typeset? = null

    /**
     * 内容重力
     */
    @GravityInt
    open var contentGravity: Int? = null

    //</editor-fold>

    //<editor-fold desc="单行配置 SingleLine">

    /**
     * 单行权重
     */
    var singleLineWeight = 1f

    //</editor-fold>

    //<editor-fold desc="内部 Internal">

    /**
     * 边界信息
     */
    var marginBoundary: Boundary = Boundary()
        internal set

    /**
     * 填充信息
     */
    var paddingBoundary: Boundary = Boundary()
        internal set

    /**
     * 全局位置
     */
    var globalPosition: Int = -1

    /**
     * 组数量
     */
    var groupCount = -1
        internal set

    /**
     * 当前组组索引
     */
    var groupIndex = -1
        internal set

    /**
     * 组中的项数量
     */
    var itemCountForGroup = -1
        internal set

    /**
     * 组中的位置
     */
    var positionForGroup = -1
        internal set

    /**
     * 项目跨度
     */
    internal var itemSpan = 2520

    /**
     * 项目跨度索引
     */
    internal var spanIndex = 0

    /**
     * 下一项为单列
     */
    internal var nextItemIsSingleColumn = false

    /**
     * 是否属于单行项目
     */
    var isSingleLineItem = false
        internal set

    /**
     * 单行项目数
     */
    var singleLineCount = -1
        internal set

    /**
     * 单行索引
     */
    var singleLineIndex = -1
        internal set


    /**
     * 反重复代码
     */
    internal val antiRepeatCode = System.currentTimeMillis() + Random.nextLong(10000)

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, Any?>? = null

    //</editor-fold>

    //<editor-fold desc="接口 interface">

    /**
     * 联动接口
     */
    internal var linkageBlock: FormLinkageBlock? = null

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
     * 设置联动接口
     */
    fun linkage(block: FormLinkageBlock?) {
        linkageBlock = block
    }

    /**
     * 设置自定义输出监听
     */
    fun customOutput(block: ((json: JSONObject) -> Unit)?) {
        customOutputBlock = block
    }

    /**
     * 检查数据正确性
     */
    open fun checkDataCorrectness(): Boolean {
        return if (isMust) content != null else true
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