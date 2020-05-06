package com.finlite.admin.kotlintrip.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.database.DBHelper
import com.finlite.admin.kotlintrip.interfaces.OnItemClickListener
import com.finlite.admin.kotlintrip.models.ExpenseModel

class ExpenseAdapter(private var context: Context, private var expenseModels: List<ExpenseModel>,
                     private var onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<ExpenseAdapter.MyViewHolder>() {

    private var dbHelper: DBHelper = DBHelper(context)

    override fun getItemCount(): Int {
        return expenseModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_activity_project_expense_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var expenseModel = expenseModels.get(position)

        holder.tvDescription.setText(expenseModel.description)
        holder.tvPrice.setText(expenseModel.expensePrice)
        holder.tvPaidBy.setText(dbHelper.getMemberName(expenseModel.memberId))

        holder.llParent.setOnClickListener { onItemClickListener.onClick(position) }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvDescription: TextView = itemView?.findViewById(R.id.tvDescription) as TextView
        var tvPrice: TextView = itemView?.findViewById(R.id.tvPrice) as TextView
        var tvPaidBy: TextView = itemView?.findViewById(R.id.tvPaidBy) as TextView

        var llParent: LinearLayout = itemView?.findViewById(R.id.llParent) as LinearLayout
    }
}