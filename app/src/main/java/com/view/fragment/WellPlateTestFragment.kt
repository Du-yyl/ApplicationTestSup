package com.view.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.applicationtest.R
import com.example.applicationtest.databinding.FragmentWellPlateBinding
import com.viewModel.AdapterWellPlateElementViewModel
import java.util.Queue


/**
 * @time :2024/1/24 11:18 40
 * @className :WellPlateFragment
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class WellPlateTestFragment : Fragment() {
    private lateinit var binding: FragmentWellPlateBinding

    var row = 12
    var cloumn = 12

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWellPlateBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        functionBind()
    }

    /**
     * 监听函数绑定
     */
    private fun functionBind() {

        // 列变化
        binding.wellplateColumnValue.addTextChangedListener {
            cloumn = try {
                it.toString().toInt()
            } catch (e: Exception) {
                0
            }

        }
        // 行变化
        binding.wellplateRowValue.addTextChangedListener {
            row = try {
                it.toString().toInt()
            } catch (e: Exception) {
                0
            }
        }

        // 按钮点击更新
        binding.wellplateUpdateRowColumn.setOnClickListener{
            binding.gridView1.updateRowOrColumn(row, cloumn)
        }
    }
}
