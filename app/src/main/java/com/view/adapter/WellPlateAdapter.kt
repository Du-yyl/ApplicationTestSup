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

// 在 WellPlateAdapter 类中添加以下代码

class WellPlateAdapter(showcaseList: List<AdapterWellPlateElementViewModel>) :
    RecyclerView.Adapter<WellPlateAdapter.ViewHolder>() {

    private var showcaseList: MutableList<AdapterWellPlateElementViewModel> =
        showcaseList.toMutableList()

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis: Long = 500 // 设置添加数据的间隔时间，单位为毫秒

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

    fun setData(data: List<AdapterWellPlateElementViewModel>) {

        for (datum in data) {
            Thread.sleep(100)
            handler.postDelayed({
                Log.d("WellPlateAdapter", "addItem: 更新数据")
                addItem(datum)
            }, delayMillis)
        }
    }

    // 逐个添加数据的方法
    fun addItem(dataModel: AdapterWellPlateElementViewModel) {
        showcaseList.add(dataModel)
        notifyItemInserted(showcaseList.size - 1)

        // 添加数据后，延迟一段时间继续添加下一条数据
//        handler.postDelayed({
//            Log.d("WellPlateAdapter", "addItem: 更新数据")
//            // 在这里添加下一条数据的逻辑
//            // 例如：addItem(nextDataModel)
//        }, delayMillis)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adapterWellPlateText: TextView = itemView.findViewById(R.id.adapter_well_plate_text)
    }
}
