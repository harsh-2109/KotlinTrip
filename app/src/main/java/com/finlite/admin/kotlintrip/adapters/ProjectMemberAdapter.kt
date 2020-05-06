package com.finlite.admin.kotlintrip.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.interfaces.OnMemberItemClickListener
import com.finlite.admin.kotlintrip.models.ProjectMemberModel

class ProjectMemberAdapter(private var context: Context, private var projectMemberModels: List<ProjectMemberModel>,
                           private var onItemClickListener: OnMemberItemClickListener)
    : RecyclerView.Adapter<ProjectMemberAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return projectMemberModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_activity_project_member_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var projectMemberModel = projectMemberModels.get(position)

        holder.tvMemberName.text = projectMemberModel.name
        holder.tvMemberEmailId.text = projectMemberModel.email

        holder.tvEdit.setOnClickListener { onItemClickListener.onEditClick(position) }
        holder.tvDelete.setOnClickListener { onItemClickListener.onDeleteClick(position) }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvMemberName: TextView = itemView!!.findViewById(R.id.tvMemberName)
        var tvMemberEmailId: TextView = itemView!!.findViewById(R.id.tvMemberEmailId)
        var tvEdit: TextView = itemView!!.findViewById(R.id.tvEdit)
        var tvDelete: TextView = itemView!!.findViewById(R.id.tvDelete)
    }
}