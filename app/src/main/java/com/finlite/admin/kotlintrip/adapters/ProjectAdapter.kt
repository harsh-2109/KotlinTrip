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
import com.finlite.admin.kotlintrip.models.ProjectModel

class ProjectAdapter(private var context: Context, private var projectModels: List<ProjectModel>,
                     private var onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<ProjectAdapter.MyViewHolder>() {

    private var dbHelper: DBHelper = DBHelper(context)

    override fun getItemCount(): Int {
        return projectModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_activity_main, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var tripModel = projectModels.get(position)

        holder.tvProjectName.text = tripModel.name
        holder.tvTotalMembers.text = dbHelper.getMemberCount(tripModel.id).toString()
        holder.tvTotalExpenses.text = dbHelper.getExpenseCount(tripModel.id).toString()

        holder.llParent.setOnClickListener { onItemClickListener.onClick(position) }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var llParent: LinearLayout = itemView!!.findViewById(R.id.llParent)

        var tvProjectName: TextView = itemView!!.findViewById(R.id.tvProjectName)
        var tvTotalMembers: TextView = itemView!!.findViewById(R.id.tvTotalMembers)
        var tvTotalExpenses: TextView = itemView!!.findViewById(R.id.tvTotalExpenses)
    }
}