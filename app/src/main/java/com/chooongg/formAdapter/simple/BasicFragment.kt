package com.chooongg.formAdapter.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chooongg.formAdapter.FormHelper
import com.chooongg.formAdapter.data.addText
import com.chooongg.formAdapter.simple.databinding.FragmentBasicBinding

class BasicFragment : Fragment() {

    private lateinit var binding: FragmentBasicBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.formView.helper = FormHelper().apply {
            plusPart {
                plusGroup {
                    addText("Text", "text") {
                        content = "FormText"
                    }
                }
            }
        }
    }
}