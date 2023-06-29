package com.chooongg.formAdapter.simple

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.addButton
import com.chooongg.formAdapter.addDivider
import com.chooongg.formAdapter.addInput
import com.chooongg.formAdapter.addSelector
import com.chooongg.formAdapter.addText
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.item.FormButton
import com.chooongg.formAdapter.option.Option
import com.chooongg.formAdapter.simple.databinding.FragmentBasicBinding
import com.chooongg.formAdapter.style.MaterialCardElevatedStyle
import com.chooongg.formAdapter.style.MaterialCardFilledStyle
import com.chooongg.formAdapter.typeset.VerticalTypeset

class BasicFragment : Fragment() {

    private lateinit var binding: FragmentBasicBinding

    private val viewModel: BasicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.formView.adapter = viewModel.adapter
        binding.btnRefresh.setOnClickListener {
            viewModel.adapter.plusPart(0) {
                dynamicPartMinGroupCount = 3
                dynamicPartCreateGroupListener {
                    addText("Text", "text") {
                        content = "FormText"
                    }
                    addText("Text", "text") {
                        content = "FormText"
                    }
                    addText("Text", "text") {
                        content = "FormText"
                    }
                    addText("Text", "text") {
                        content = "FormText"
                    }
                }
            }
        }
    }

    class BasicViewModel : ViewModel() {
        val adapter = FormAdapter(true) {
            plusPart {
                plusGroup {
                    addText("FormAdapter") {
                        content = "Android FormAdapter"
                    }
                    addButton("IsEditable", "isEditable") {
                        enableMode = FormEnableMode.ALWAYS
                    }
                    addButton("Button", "button") {
                        buttonStyle = FormButton.ButtonStyle.ELEVATED
                        contentGravity = Gravity.START
                    }
                    addButton("Button", "button") {
                        buttonStyle = FormButton.ButtonStyle.OUTLINED
                        contentGravity = Gravity.END
                    }
                    addButton("Button", "button") {
                        buttonStyle = FormButton.ButtonStyle.TEXT
                        contentGravity = Gravity.CENTER
                    }
                    addButton("Button", "button") {
                        buttonStyle = FormButton.ButtonStyle.TONAL
                        contentGravity = Gravity.CENTER_HORIZONTAL
                    }
                    addButton("Button", "button") {
                        buttonStyle = FormButton.ButtonStyle.UN_ELEVATED
                    }
                }
            }
            (0..6).forEach { _ ->
                plusPart(MaterialCardElevatedStyle()) {
                    plusGroup {
                        name = "Title"
                        addInput("Input", "input") {
                            maxLines = 1
                            contentGravity = Gravity.NO_GRAVITY
                        }
                        addSelector("Selector", "selector") {
                            isMust = true
                            content = "FormSelector"
                            localOptions(
                                listOf(
                                    Option("选项1"),
                                    Option("选项2"),
                                    Option("选项3"),
                                    Option("选项4")
                                )
                            )
                        }
                        addButton("Button", "button") {

                        }
                        addText("Text", "text") {
                            content = "FormText"
                        }
                        addDivider() {
                            matchParentWidth = true
                        }
                        addText("Text", "text") {
                            content = "FormText"
                        }
                        addText("Text", "text") {
                            content = "FormText"
                        }
                        addText("Text", "text") {
                            content = "FormText"
                        }
                        addText("Text", "text") {
                            content = "FormText"
                        }
                    }
                }
            }
            plusPart(MaterialCardFilledStyle(null, VerticalTypeset)) {
                plusGroup {
                    name = "Title"
                    addText("Text", "text") {
                        content = "FormText"
                    }
                    addText("Text", "text") {
                        content = "FormText"
                    }
                    addText("Text", "text") {
                        content = "FormText"
                    }
                    addText("Text", "text") {
                        content = "FormText"
                    }
                    addText("Text", "text") {
                        content = "FormText"
                    }
                }
            }
        }
    }
}