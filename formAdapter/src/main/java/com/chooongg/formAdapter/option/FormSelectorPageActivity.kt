package com.chooongg.formAdapter.option

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.formAdapter.FormViewHolder
import com.chooongg.formAdapter.R
import com.chooongg.formAdapter.databinding.FormActivityAdapterSelectorPageBinding
import com.chooongg.formAdapter.item.FormSelector
import com.chooongg.utils.SpannableStyle
import com.chooongg.utils.ext.attrBoolean
import com.chooongg.utils.ext.attrColor
import com.chooongg.utils.ext.attrResourcesId
import com.chooongg.utils.ext.doOnClick
import com.chooongg.utils.ext.dp2px
import com.chooongg.utils.ext.hideIME
import com.chooongg.utils.ext.setText
import com.chooongg.utils.ext.style
import com.chooongg.utils.ext.withMain
import com.google.android.material.motion.MotionUtils
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FormSelectorPageActivity : AppCompatActivity() {

    internal object Controller {
        var formSelector: FormSelector? = null
        var resultBlock: ((BaseOption?) -> Unit)? = null
    }

    private val binding by lazy { FormActivityAdapterSelectorPageBinding.inflate(layoutInflater) }

    private val concatAdapter = ConcatAdapter()

    private val optionAdapter = OptionAdapter(Controller.formSelector?.content) {
        Controller.resultBlock?.invoke(it)
        finishAfterTransition()
        Controller.formSelector = null
        Controller.resultBlock = null
    }.apply { concatAdapter.addAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configWindow()
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hideIME()
                finishAfterTransition()
            }
        })
        if (Controller.formSelector == null || Controller.resultBlock == null) {
            finishAfterTransition()
            return
        }
        if (Controller.formSelector?.isMust == false) {
            val nullAdapter = NullAdapter(Controller.formSelector?.hint) {
                Controller.resultBlock?.invoke(it)
                finishAfterTransition()
                Controller.formSelector = null
                Controller.resultBlock = null
            }
            concatAdapter.addAdapter(0, nullAdapter)
        }
        binding.recyclerView.adapter = concatAdapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    binding.inputSearch.clearFocus()
                    hideIME()
                }
            }
        })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val options = Controller.formSelector?.options
        optionAdapter.submit(options)
        var job: Job? = null
        binding.editSearch.doAfterTextChanged {
            job?.cancel()
            job = null
            val search = binding.editSearch.text?.toString()
            if (search.isNullOrEmpty()) {
                optionAdapter.submit(options)
            } else {
                job = lifecycleScope.launch {
                    val searchList = options?.filter {
                        val regex = Regex(".*${search.lowercase()}.*")
                        if (regex.matches(it.getName().lowercase())) {
                            return@filter true
                        }
                        val secondary = it.getSecondaryName()
                        if (secondary != null) {
                            regex.matches(secondary.lowercase())
                        } else false
                    }
                    withMain { optionAdapter.submit(searchList) }
                }
            }
        }
    }

    private fun configWindow() {
        createEdgeToEdge()
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            sharedElementsUseOverlay = false
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            exitTransition = null
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            reenterTransition = null
            transitionBackgroundFadeDuration = MotionUtils.resolveThemeDuration(
                context, com.google.android.material.R.attr.motionDurationLong1, -1
            ).toLong()
        }
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        findViewById<FrameLayout>(android.R.id.content).transitionName = "FormSelectorPage"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildContainerTransform(true)
        window.sharedElementReturnTransition = buildContainerTransform(false)
    }

    private fun createEdgeToEdge() {
        if (attrBoolean(androidx.appcompat.R.attr.windowActionBar, false)) return
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
    }

    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        transform.addTarget(android.R.id.content)
        transform.fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        transform.pathMotion = MaterialArcMotion()
        return transform
    }

    private class NullAdapter(
        private val hint: CharSequence?,
        private val selectBlock: (BaseOption?) -> Unit
    ) : RecyclerView.Adapter<FormViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FormViewHolder(MaterialTextView(parent.context).apply {
                val size = dp2px(8f)
                setPadding(size, size * 2, size, size * 2)
                val shapeResId = parent.attrResourcesId(
                    com.google.android.material.R.attr.shapeAppearanceCornerMedium, 0
                )
                val shape = ShapeAppearanceModel.builder(parent.context, shapeResId, 0).build()
                background = RippleDrawable(
                    ColorStateList.valueOf(attrColor(androidx.appcompat.R.attr.colorControlHighlight)),
                    null,
                    MaterialShapeDrawable(shape)
                )
                layoutParams = MarginLayoutParams(
                    MarginLayoutParams.MATCH_PARENT,
                    MarginLayoutParams.WRAP_CONTENT
                ).apply {
                    leftMargin = size
                    rightMargin = size
                }
            })

        override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
            with(holder.itemView as MaterialTextView) {
                hint = this@NullAdapter.hint ?: resources.getString(R.string.formDefaultHintSelect)
                doOnClick {
                    selectBlock.invoke(null)
                }
            }
        }

        override fun getItemCount() = 1
    }

    private class OptionAdapter(
        private val selected: Any?,
        private val selectBlock: (BaseOption?) -> Unit
    ) : RecyclerView.Adapter<FormViewHolder>() {

        private val asyncDiffer =
            AsyncListDiffer(this, object : DiffUtil.ItemCallback<BaseOption>() {
                override fun areItemsTheSame(oldItem: BaseOption, newItem: BaseOption) =
                    oldItem.getName() == newItem.getName()

                override fun areContentsTheSame(oldItem: BaseOption, newItem: BaseOption) =
                    oldItem.getValue() == newItem.getValue()
            })

        fun submit(options: List<BaseOption>?) {
            asyncDiffer.submitList(options)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FormViewHolder(MaterialTextView(parent.context).apply {
                val size = dp2px(8f)
                gravity = Gravity.CENTER_VERTICAL
                setPadding(size, size * 2, size, size * 2)
                val shapeResId = parent.attrResourcesId(
                    com.google.android.material.R.attr.shapeAppearanceCornerMedium, 0
                )
                val shape = ShapeAppearanceModel.builder(parent.context, shapeResId, 0).build()
                background = RippleDrawable(
                    ColorStateList.valueOf(attrColor(androidx.appcompat.R.attr.colorControlHighlight)),
                    null,
                    MaterialShapeDrawable(shape)
                )
                layoutParams = MarginLayoutParams(
                    MarginLayoutParams.MATCH_PARENT,
                    MarginLayoutParams.WRAP_CONTENT
                ).apply {
                    leftMargin = size
                    rightMargin = size
                }
            })

        override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
            val option = asyncDiffer.currentList[position]
            with(holder.itemView as MaterialTextView) {
                val span = SpannableStyle(option.getName()).apply {
                    if (option == selected) {
                        setForegroundColor(attrColor(androidx.appcompat.R.attr.colorPrimary))
                    } else {
                        setForegroundColor(attrColor(com.google.android.material.R.attr.colorOnSurface))
                    }
                }
                val secondaryName = option.getSecondaryName()
                if (secondaryName != null) {
                    span.plus(" ")
                    span.plus(secondaryName.style {
                        setTextSizeRelative(0.8f)
                        if (option == selected) {
                            setForegroundColor(attrColor(androidx.appcompat.R.attr.colorPrimary))
                        } else {
                            setForegroundColor(attrColor(com.google.android.material.R.attr.colorOutline))
                        }
                    })
                }
                setText(span)
                doOnClick {
                    selectBlock(option)
                }
            }
        }

        override fun getItemCount() = asyncDiffer.currentList.size
    }
}