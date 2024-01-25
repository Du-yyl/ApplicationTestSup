package com.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.applicationtest.R
import com.example.applicationtest.databinding.FragmentWellplateBinding
import es.dmoral.toasty.Toasty


/**
 * @time :2024/1/24 11:18 40
 * @className :WellPlateFragment
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class WellPlateFragment : Fragment() {

    private lateinit var binding: FragmentWellplateBinding

    var row = 2
    var cloumn = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWellplateBinding.inflate(inflater, container, false)

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

            binding.wellpateWellPlate.updateRowsOrColumns(row, cloumn)
        }
        // 行变化
        binding.wellplateRowValue.addTextChangedListener {
            row = try {
                it.toString().toInt()
            } catch (e: Exception) {
                0
            }
            binding.wellpateWellPlate.updateRowsOrColumns(row, cloumn)
        }
    }
}
