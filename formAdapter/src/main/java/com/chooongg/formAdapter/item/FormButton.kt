package com.chooongg.formAdapter.item

import androidx.annotation.RestrictTo
import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.enum.FormVisibilityMode
import com.chooongg.formAdapter.provider.FormButtonProvider
import com.chooongg.formAdapter.typeset.NoneTypeset
import com.chooongg.formAdapter.typeset.Typeset
import com.google.android.material.button.MaterialButton

fun FormCreator.addButton(
    name: CharSequence?, field: String? = null, block: (FormButton.() -> Unit)? = null
) = add(FormButton(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addButton(
    @StringRes nameRes: Int?, field: String? = null, block: (FormButton.() -> Unit)? = null
) = add(FormButton(nameRes, null, field).apply { block?.invoke(this) })

class FormButton(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

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

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    override var menuText: CharSequence? = null

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    override var menuIconRes: Int? = null

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    override var menuIconSize: Int? = null

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    override var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    override fun getItemProvider(adapter: FormAdapter) = FormButtonProvider
}