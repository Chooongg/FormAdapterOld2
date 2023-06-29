package com.chooongg.formAdapter.item

import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.enum.FormVisibilityMode
import com.chooongg.formAdapter.provider.FormButtonProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.button.MaterialButton

class FormButton(name: CharSequence?, field: String?) : BaseForm(name, field) {

    enum class ButtonStyle {
        DEFAULT, TEXT, TONAL, OUTLINED, ELEVATED, UN_ELEVATED
    }

    var buttonStyle: ButtonStyle = ButtonStyle.DEFAULT

    var iconRes: Int? = null

    var iconSize: Int? = null

    @MaterialButton.IconGravity
    var iconGravity: Int = MaterialButton.ICON_GRAVITY_TEXT_START

    var iconTint: FormColorStateListBlock? = null

    override var typeset: Typeset? = NoneTypeset

    @Deprecated("invalid property", ReplaceWith("name"))
    override var menuText: CharSequence? = null

    @Deprecated("invalid property", ReplaceWith("iconRes"))
    override var menuIconRes: Int? = null

    @Deprecated("invalid property", ReplaceWith("iconSize"))
    override var menuIconSize: Int? = null

    @Deprecated("invalid property")
    override var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    override fun getItemProvider(adapter: FormAdapter) = FormButtonProvider
}