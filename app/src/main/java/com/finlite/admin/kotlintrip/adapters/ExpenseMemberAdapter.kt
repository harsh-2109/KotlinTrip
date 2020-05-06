package com.finlite.admin.kotlintrip.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.activities.AddExpenseActivity
import com.finlite.admin.kotlintrip.models.ExpenseMemberModel
import java.text.DecimalFormat

class ExpenseMemberAdapter(private var context: Context, private var expenseMemberModels: List<ExpenseMemberModel>)
    : RecyclerView.Adapter<ExpenseMemberAdapter.MyViewHolder>() {

    private var price: Int = 0

    override fun getItemCount(): Int {
        return expenseMemberModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_activity_expense_member_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var expenseMemberModel = expenseMemberModels.get(position)

        holder.tvMemberName.text = expenseMemberModel.name
        holder.tvPrice.text = expenseMemberModel.price.toString()
        if (expenseMemberModel.check)
            holder.tvAdd.text = "Minus"
        else
            holder.tvAdd.text = "Add"

        holder.tvAdd.setOnClickListener {
            expenseMemberModel.check = !expenseMemberModel.check
            addPrice(price)
            isCheckable()
        }
    }

    fun refresh(price: Int) {
        addPrice(price)
        isCheckable()
        notifyDataSetChanged()
    }

    fun addPrice(price: Int) {
        this.price = price
        var count: Int = 0
        for (expense: ExpenseMemberModel in expenseMemberModels) {
            if (expense.check)
                count++
        }
        Log.e("asdf", "asdf " + count)

        val divide: Float = price.toFloat() / count
        for (expense: ExpenseMemberModel in expenseMemberModels) {
            if (expense.check) {
                expense.price = DecimalFormat("##.##").format(divide).toFloat()
            } else {
                expense.price = 0F
            }
        }
        notifyDataSetChanged()
    }

    fun isCheckable() {
        var status = true

        for (expense: ExpenseMemberModel in expenseMemberModels)
            if (!expense.check)
                status = false
        Log.e("ischeckable", "" + status)
        (context as AddExpenseActivity).setCheckAll(status)
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvMemberName: TextView = itemView!!.findViewById(R.id.tvMemberName)
        var tvAdd: TextView = itemView!!.findViewById(R.id.tvAdd)
        var tvPrice: TextView = itemView!!.findViewById(R.id.tvPrice)
    }
}