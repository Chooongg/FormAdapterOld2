package com.chooongg.formAdapter.typeset

import android.view.ViewGroup
import com.chooongg.formAdapter.FormManager
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.enum.FormEmsMode
import com.chooongg.formAdapter.item.BaseForm

object NoneTypeset : Typeset(FormManager.emsSize, FormEmsMode.NONE) {

    override fun onCreateTypesetLayout(parent: ViewGroup) = null

    override fun onBindTypesetLayout(holder: FormViewHolder, item: BaseForm) = Unit

}