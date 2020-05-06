package com.finlite.admin.kotlintrip.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.interfaces.OnItemClickListener
import com.finlite.admin.kotlintrip.models.SettleModel

class SettleAdapter(private var context: Context, private var settleModels: List<SettleModel>,
                    private var onItemClickListener: OnItemClickListener?)
    : RecyclerView.Adapter<SettleAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return settleModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_activity_settle, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var settleModel = settleModels.get(position)

        holder.tvText.text = settleModel.from + " Paid To " + settleModel.to

        holder.tvBalance.text = settleModel.balalnce.toString()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvText: TextView = itemView!!.findViewById(R.id.tvText)
        var tvBalance: TextView = itemView!!.findViewById(R.id.tvBalance)
    }
}