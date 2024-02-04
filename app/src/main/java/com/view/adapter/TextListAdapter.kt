package com.view.adapter

import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationtest.R


/**
 * @time :2024/1/31 15:44 51
 * @className :WellPlateShowRowAdapter
 * @package :ApplicationTest
 * @author :weiyp
 * @description :
 * <p>
 */
class TextListAdapter(private val texts: List<String>) :
    RecyclerView.Adapter<TextListAdapter.RecyclerViewViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextListAdapter.RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(com.example.applicationtest.R.layout.adapter_text_list, parent, false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val numberItem = texts[position]
        val layoutParams = holder.textView.layoutParams

        holder.textView.text = numberItem
    }

    override fun getItemCount(): Int {
        return texts.size
    }

    class RecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text_list_text)
    }
}


