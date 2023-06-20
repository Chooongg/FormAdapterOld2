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
import com.chooongg.formAdapter.simple.databinding.FragmentBasicBinding
import com.chooongg.formAdapter.style.CardStyle
import com.chooongg.formAdapter.style.MaterialCardElevatedStyle

class BasicFragment : Fragment() {

    private lateinit var binding: FragmentBasicBinding

    private val viewModel: BasicViewModel by viewModels()

    val adapter = FormAdapter(true) {
        plusPart(CardStyle()) {
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
            }
        }
        plusPart(MaterialCardElevatedStyle()) {
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
            }
        }
        plusPart(MaterialCardElevatedStyle(40f)) {
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
            }
        }
        plusPart {
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
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.formView.adapter = adapter
    }

    class BasicViewModel : ViewModel() {

    }
}