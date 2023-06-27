package com.chooongg.formAdapter.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.addSelector
import com.chooongg.formAdapter.data.addText
import com.chooongg.formAdapter.option.Option
import com.chooongg.formAdapter.simple.databinding.FragmentBasicBinding
import com.chooongg.formAdapter.style.MaterialCardElevatedStyle
import com.chooongg.formAdapter.style.MaterialCardFilledStyle

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
            (0..6).forEach { _ ->
                plusPart(MaterialCardElevatedStyle()) {
                    plusGroup {
//                        name = "Title"
                        addSelector("Selector", "selector") {
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
            plusPart(MaterialCardFilledStyle()) {
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