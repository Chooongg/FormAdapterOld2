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
import com.chooongg.formAdapter.addLabel
import com.chooongg.formAdapter.addMenu
import com.chooongg.formAdapter.addSelector
import com.chooongg.formAdapter.addSwitch
import com.chooongg.formAdapter.addTip
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.enum.FormVisibilityMode
import com.chooongg.formAdapter.option.Option
import com.chooongg.formAdapter.simple.databinding.FragmentBasicBinding
import com.chooongg.formAdapter.style.CardElevatedStyle

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
            viewModel.adapter.isEditable = !viewModel.adapter.isEditable
        }
    }

    class BasicViewModel : ViewModel() {
        val adapter = FormAdapter(true) {
            for (i in 0..6) plusPart(CardElevatedStyle()) {
                plusGroup {
                    addLabel("Android FormAdapter") {
                        contentGravity = Gravity.CENTER_HORIZONTAL
                    }
                    addButton("IsEditable", "isEditable") {
                        enableMode = FormEnableMode.ALWAYS
                        menuText = ""
                    }
                    addTip("这是一个标签") {
                        visibilityMode = FormVisibilityMode.ONLY_EDIT
                        contentGravity = Gravity.CENTER_HORIZONTAL
                    }
                    addInput("Input", "input") {
                        visibilityMode = FormVisibilityMode.ONLY_EDIT
                        maxLines = 3
                    }
                    addSelector("Selector", "selector") {
                        content = "FormSelector"
                        openMode = FormSelectorOpenMode.PAGE
                        localOptions(
                            listOf(
                                Option("张三"),
                                Option("李四"),
                                Option("王五"),
                                Option("赵六"),
                                Option("田七"),
                                Option("周八"),
                                Option("吴九"),
                                Option("郑十"),
                            )
                        )
                    }
                    addSelector("Selector", "selector") {
                        isMust = true
                        content = "FormSelector"
                        localOptions(
                            listOf(
                                Option("选项1"),
                                Option("选项2"),
                                Option("选项3"),
                                Option("选项4"),
                                Option("选项5"),
                                Option("选项6"),
                                Option("选项7"),
                                Option("选项8"),
                                Option("选项9"),
                                Option("选项10"),
                            )
                        )
                    }
                    addDivider() {
                        matchParentWidth = true
                    }
                    addSwitch("Switch", "switch") {

                    }
                    addSwitch("Switch", "switch") {

                    }
                    addMenu("设置项", "setting") {
                        content = "新版本"
                        badgeNumber = 100
                        badgeMaxNumber = 99
                    }
                }
            }
        }
    }
}