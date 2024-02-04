package com.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import com.example.applicationtest.databinding.FragmentWellPlateBinding
import com.example.applicationtest.databinding.FragmentWellPlateShowBinding
import com.view.adapter.TextListAdapter
import com.view.customize.CustomGridLayoutManager

/**
 * @time :2024/1/31 15:19 58
 * @className :WellPlateShowFragment
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class WellPlateShowFragment : Fragment() {
    private lateinit var binding: FragmentWellPlateShowBinding
 private var row = 0
    private var column = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWellPlateShowBinding.inflate(inflater, container, false)

//        // 行
//        setRow(10)
//
//        // 列
//        binding.wellPlateShowColumn.layoutManager =
//            CustomGridLayoutManager(requireContext(), 1).apply {
//                setScrollEnabled(false)
//            }
//        setColumn(5)


        return binding.root
    }

    fun setRow(row: Int) {
//        binding.wellPlateShowRow.layoutManager =
//            CustomGridLayoutManager(requireContext(), row).apply {
//                setScrollEnabled(false)
//            }
//        val ss = mutableListOf<String>()
//        for (r in 0 until row) {
//            ss.add(r.toString())
//        }
//        binding.wellPlateShowRow.adapter = TextListAdapter(ss)
//        this.row = row
    }

    fun setColumn(column: Int) {
//        val ss = mutableListOf<String>()
//        for (r in 0 until column) {
//            ss.add(r.toString())
//        }
//
//        binding.wellPlateShowColumn.adapter = TextListAdapter(ss)
//        Log.d("WellPlateShowFragment", "setColumn: width: ${binding.wellPlateShowRow.width}")
//        Log.d("WellPlateShowFragment", "setColumn: height: ${binding.wellPlateShowColumn.height}")
//        this.column = column
    }

    fun updateRowOrColumn(row: Int, column: Int) {


        this.column = column
        this.row = row
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}