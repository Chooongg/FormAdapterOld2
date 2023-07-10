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
import com.chooongg.formAdapter.enum.FormEnableMode
import com.chooongg.formAdapter.enum.FormOptionLoadMode
import com.chooongg.formAdapter.enum.FormSelectorOpenMode
import com.chooongg.formAdapter.enum.FormTimeMode
import com.chooongg.formAdapter.enum.FormVisibilityMode
import com.chooongg.formAdapter.item.addButton
import com.chooongg.formAdapter.item.addCheckBox
import com.chooongg.formAdapter.item.addDivider
import com.chooongg.formAdapter.item.addInput
import com.chooongg.formAdapter.item.addInputAutoComplete
import com.chooongg.formAdapter.item.addLabel
import com.chooongg.formAdapter.item.addMenu
import com.chooongg.formAdapter.item.addRating
import com.chooongg.formAdapter.item.addSelector
import com.chooongg.formAdapter.item.addSlider
import com.chooongg.formAdapter.item.addSwitch
import com.chooongg.formAdapter.item.addTime
import com.chooongg.formAdapter.item.addTip
import com.chooongg.formAdapter.option.Option
import com.chooongg.formAdapter.simple.databinding.FragmentBasicBinding
import com.chooongg.formAdapter.style.CardFilledStyle
import kotlinx.coroutines.delay

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
            plusPart {
                plusGroup {
                    addButton("IsEditable", "isEditable") {
                        this.enableMode = FormEnableMode.ALWAYS
                    }
                }
            }
            for (i in 0..6) plusPart(CardFilledStyle()) {
                plusGroup {
                    addLabel("Android FormAdapter") {
                        contentGravity = Gravity.CENTER_HORIZONTAL
                    }
                    addTip("这是一个标签") {
                        visibilityMode = FormVisibilityMode.ONLY_EDIT
                        contentGravity = Gravity.CENTER_HORIZONTAL
                    }
                    addSelector("Selector", "selector")
                    addSelector("Selector", "selector") {
                        content = "FormSelector"
                        openMode = FormSelectorOpenMode.PAGE
                        optionLoadMode = FormOptionLoadMode.EMPTY
                        localOptions(
                            listOf(
                                Option("张三", "130181199505087310"),
                                Option("李四", "130181199505087310"),
                                Option("王五", "13123451341236"),
                                Option("赵六", "167835789"),
                                Option("田七", "981234867"),
                                Option("周八", "731897564"),
                                Option("吴九", "378189"),
                                Option("郑十", "8515968"),
                            )
                        )
                    }
                    addSlider("Slider", "slider") {
                        stepSize = 10f
                        formatter {
                            "${it.toInt()}块"
                        }
                    }
                    addInput("Input", "input") {
                        placeholderText = "请输入手机号"
                    }
                    addInput("Input", "input")
                    addInputAutoComplete("AutoComplete", "inputAutoComplete") {
                        optionLoader {
                            delay(5000)
                            listOf(
                                "张三",
                                "李四",
                                "王五",
                                "赵六",
                                "田七",
                                "周八",
                                "吴九",
                                "郑十"
                            )
                        }
                    }
                    addSelector("Selector", "selector") {
                        isMust = true
                        localOptions(
                            listOf(
                                Option("张三", "130181199505087310"),
                                Option("李四", "130181199505087310"),
                                Option("王五", "13123451341236"),
                                Option("赵六", "167835789"),
                                Option("田七", "981234867"),
                                Option("周八", "731897564"),
                                Option("吴九", "378189"),
                                Option("郑十", "8515968"),
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
                    addTime("Time", "time") {
                        timeMode = FormTimeMode.TIME
                    }
                    addTime("Date", "date") {
                        timeMode = FormTimeMode.DATE
                    }
                    addTime("Date Time", "dateTime") {
                        timeMode = FormTimeMode.DATE_TIME
                    }
                    addRating("Rating", "rating") {

                    }
                    addInputAutoComplete("AutoComplete", "inputAutoComplete1") {
                        optionLoader {
                            delay(5000)
                            listOf(
                                "张三",
                                "李四",
                                "王五",
                                "赵六",
                                "田七",
                                "周八",
                                "吴九",
                                "郑十"
                            )
                        }
                    }
                    addCheckBox("CheckBox", "checkBox") {
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
                        content = arrayListOf(Option("郑十"))
                    }
                }
            }
        }
    }
}