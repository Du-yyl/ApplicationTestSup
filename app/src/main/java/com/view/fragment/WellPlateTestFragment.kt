package com.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.lifecycle.lifecycleScope
import com.example.applicationtest.databinding.FragmentWellPlateBinding
import com.view.adapter.WellPlateAdapter
import com.view.customize.CustomGridLayoutManager
import com.viewModel.AdapterWellPlateElementViewModel
import kotlinx.coroutines.launch


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

        Queue.getInstance()

        Queue.func = {
            binding.gridView1.setData(it)
//            binding.gridView1.updateRowOrColumn(row, cloumn)
        }


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

            setData()
        }
        // 行变化
        binding.wellplateRowValue.addTextChangedListener {
            row = try {
                it.toString().toInt()
            } catch (e: Exception) {
                0
            }
            setData()
        }
    }

    fun setData() {
        val adapterList = mutableListOf<AdapterWellPlateElementViewModel>()
        for (i in 0 until row *cloumn ) {
            adapterList.add(AdapterWellPlateElementViewModel(i.toString()))
        }
        Queue.lists = adapterList
    }
}

class Queue : Thread() {


    override fun run() {
        while (true) {
            if (lists.size == 0) continue

            val ele = lists.removeAt(0)
            func?.invoke(ele)

            sleep(100)
        }
    }

    companion object {
        var lists: MutableList<AdapterWellPlateElementViewModel> = mutableListOf()

        var func: ((ele:AdapterWellPlateElementViewModel) -> Unit)? = null

        private var instance: Queue? = null

        fun getInstance(): Queue {
            return instance ?: Queue().also {
                it.isDaemon = true
                it.start()
                instance = it
            }
        }

    }
}