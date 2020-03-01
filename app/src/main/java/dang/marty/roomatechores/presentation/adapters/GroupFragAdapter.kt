package dang.marty.roomatechores.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dang.marty.roomatechores.R


/**
 *   Created by Marty Dang on 2020-01-22
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class GroupFragAdapter(private val context: Context, private val dataSet: List<String>) : BaseAdapter(){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            convertView = layoutInflater.inflate(R.layout.gridview_item, null)
        }
        val textView: TextView? = convertView?.findViewById(R.id.member_name)
        textView?.text = dataSet[position]

        return convertView
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return dataSet.size
    }

}