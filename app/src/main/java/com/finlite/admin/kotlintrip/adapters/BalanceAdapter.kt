package com.finlite.admin.kotlintrip.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.interfaces.OnItemClickListener
import com.finlite.admin.kotlintrip.models.BalanceModel

class BalanceAdapter(private var context: Context, private var balanceModels: List<BalanceModel>,
                     private var onItemClickListener: OnItemClickListener?)
    : RecyclerView.Adapter<BalanceAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return balanceModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_activity_balance, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var balanceModel = balanceModels.get(position)

        holder.tvMemberName.setText(balanceModel.memberName)
        holder.tvPaid.text = "Paid:" + balanceModel.memberTotalPaid
        holder.tvCharged.text = "Charged:" + balanceModel.divisionPaid

        holder.tvBalance.text = (balanceModel.memberTotalPaid - balanceModel.divisionPaid).toString()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvMemberName: TextView = itemView!!.findViewById(R.id.tvMemberName)
        var tvCharged: TextView = itemView!!.findViewById(R.id.tvCharged)
        var tvPaid: TextView = itemView!!.findViewById(R.id.tvPaid)
        var tvBalance: TextView = itemView!!.findViewById(R.id.tvBalance)
    }
}