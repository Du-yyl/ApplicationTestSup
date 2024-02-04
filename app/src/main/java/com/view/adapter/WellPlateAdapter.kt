package com.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationtest.R
import com.viewModel.AdapterWellPlateElementViewModel

/**
 * @time :2024/1/25 16:11 58
 * @className :RecyclerViewAdapter
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */

/**
 * 试验对象适配器
 */
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class WellPlateAdapter(list: List<AdapterWellPlateElementViewModel>) :
    RecyclerView.Adapter<WellPlateAdapter.ViewHolder>() {

    companion object {
        /**
         * 切片大小
         */
        val sliceSize = 5

        /**
         * 元素更新最低延迟时间
         */
        var delayTime: Long = 0

        /**
         * 延迟时间间隔
         */
        var delayIntervalTime = 5

        /**
         * 最大元素个数
         */
        var maxItemSize = 500
    }

    private val handler = Handler(Looper.getMainLooper())

    private var showcaseList: MutableList<AdapterWellPlateElementViewModel> = list.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_well_plate, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val planet = showcaseList[position]
        holder.adapterWellPlateText.text = planet.text
    }

    override fun getItemCount(): Int {
        return showcaseList.size
    }

    /**
     * 通过自定义数据更新展示列表
     */
    fun updateData(newDataList: List<AdapterWellPlateElementViewModel>) {
        // 元素个数不能超过 maxItemSize (500)
        if (newDataList.size > maxItemSize) throw Exception("元素个数不能超过 $maxItemSize ")

        // 如果数量相同
        if (newDataList.size == showcaseList.size) return

        // 清空数组
        showcaseList.clear()

        // 获取切片数组
        val arraySlicing = arraySlicing(
            newDataList.subList(showcaseList.size, newDataList.size)
        )

        // 增加数据
        elementRecursivelyAdd(arraySlicing.toMutableList(), delayTime)

        return

        // 如果新数组更小 或 相同
        if (newDataList.size < showcaseList.size) {
            val diffCallback = WellPlateAdapterDiffCallback(showcaseList, newDataList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)

            showcaseList = newDataList.toMutableList()
            return
        }
        // 如果新数组更大
        else {
            // 获取切片数组
            val arraySlicing = arraySlicing(
                newDataList.subList(showcaseList.size, newDataList.size)
            )

            // 清空数组
            showcaseList.clear()

            // 增加数据
            elementRecursivelyAdd(arraySlicing.toMutableList(), delayTime)
        }
    }

    /**
     * 元素递归增加
     */
    private fun elementRecursivelyAdd(
        lists: MutableList<List<AdapterWellPlateElementViewModel>>,
        delayTime: Long
    ) {
        if (lists.size == 0) return
        val removeAt = lists.removeAt(0)
        handler.postDelayed({
            showcaseList.addAll(removeAt)
            notifyItemRangeInserted(
                showcaseList.size - removeAt.size,
                showcaseList.size
            )  // 通知适配器数据已更改
            Log.d("WellPlateAdapter", "elementRecursivelyAdd: delayTime: $delayTime")
            elementRecursivelyAdd(lists, delayTime + delayIntervalTime)
        }, delayTime)
    }


    /**
     * 数组分片
     */
    private fun arraySlicing(list: List<AdapterWellPlateElementViewModel>): List<List<AdapterWellPlateElementViewModel>> {
        return if (list.size <= sliceSize) {
            // 如果原始数组长度小于等于 slice，直接封装为一个数组中的数组
            listOf(list)
        } else {
            // 如果原始数组长度大于 slice，使用 chunked 函数切割为每个长度最大为 slice 的数组
            list.chunked(sliceSize)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adapterWellPlateText: TextView = itemView.findViewById(R.id.adapter_well_plate_text)
    }

    /**
     * 不同数据状态对比
     */
    class WellPlateAdapterDiffCallback(
        private val oldList: List<AdapterWellPlateElementViewModel>,
        private val newList: List<AdapterWellPlateElementViewModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            // 判断内容是否相同，这里可以根据实际情况进行比较
            return oldItem == newItem
        }
    }
}
