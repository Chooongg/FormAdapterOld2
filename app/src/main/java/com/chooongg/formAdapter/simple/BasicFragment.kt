package com.chooongg.formAdapter.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.chooongg.formAdapter.FormAdapter
import com.chooongg.formAdapter.data.addText
import com.chooongg.formAdapter.enum.FormVisibilityMode
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
//            viewModel.adapter.findOfField("text"){
//                it.visibilityMode = FormVisibilityMode.NEVER
//            }
            viewModel.adapter.plusPart(0){
                plusGroup {
//                        groupName = "Title"
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

    class BasicViewModel : ViewModel() {
        val adapter = FormAdapter(true) {
            (0..6).forEach {
                plusPart(MaterialCardElevatedStyle()) {
                    plusGroup {
//                        groupName = "Title"
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
                    groupName = "Title"
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