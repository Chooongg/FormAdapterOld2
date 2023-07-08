package com.chooongg.formAdapter.item

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.FormColorStateListBlock
import com.chooongg.formAdapter.FormPartAdapter
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.data.FormCreator
import com.chooongg.formAdapter.provider.FormRatingProvider

fun FormCreator.addRating(
    name: CharSequence?, field: String? = null, block: (FormRating.() -> Unit)? = null
) = add(FormRating(null, name, field).apply { block?.invoke(this) })

fun FormCreator.addRating(
    @StringRes nameRes: Int?, field: String? = null, block: (FormRating.() -> Unit)? = null
) = add(FormRating(nameRes, null, field).apply { block?.invoke(this) })

class FormRating(@StringRes nameRes: Int?, name: CharSequence?, field: String?) :
    BaseForm(nameRes, name, field) {

    /**
     * 星星数量
     */
    @androidx.annotation.IntRange(from = 1)
    var numStars: Int = 5

    /**
     * 步长
     */
    @FloatRange(from = 0.0)
    var stepSize: Float = 1f

    var tint: FormColorStateListBlock? = null

    override fun getItemProvider(adapter: FormAdapter) = FormRatingProvider

    override fun getContentText(adapter: FormPartAdapter, holder: FormViewHolder): CharSequence? {
        val rating = content as? Float ?: return content?.toString()
        return holder.itemView.context.getString(R.string.formRatingFormat, rating)
    }
}