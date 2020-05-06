package com.finlite.admin.kotlintrip.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.adapters.ProjectMemberAdapter
import com.finlite.admin.kotlintrip.database.DBHelper
import com.finlite.admin.kotlintrip.interfaces.OnMemberItemClickListener
import com.finlite.admin.kotlintrip.models.ProjectMemberModel
import com.finlite.admin.kotlintrip.models.ProjectModel
import com.finlite.admin.kotlintrip.utils.EqualSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.*

class AddProjectActivity : AppCompatActivity(), OnMemberItemClickListener {

    private lateinit var projectMemberModels: ArrayList<ProjectMemberModel>
    private lateinit var projectMemberAdapter: ProjectMemberAdapter

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()

    }

    private fun init() {
        dbHelper = DBHelper(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(EqualSpacingItemDecoration(10))

        projectMemberModels = ArrayList()

        projectMemberAdapter = ProjectMemberAdapter(this, projectMemberModels, this)
        recyclerView.adapter = projectMemberAdapter

        btnAddProject.setOnClickListener { addProject() }
        tvAddMember.setOnClickListener {
            id = 0
            memberName = ""
            memberEmail = ""
            openAddMemberDialog()
        }
    }

    private fun addProject() {
        if (etProjectName.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Project name", Toast.LENGTH_SHORT).show()
            return
        }

        if (projectMemberModels.size == 0) {
            Toast.makeText(this, "Add Members", Toast.LENGTH_SHORT).show()
            return
        }

        val projectModel = ProjectModel(etProjectName.text.toString(), etProjectDescription.text.toString())
        val id = dbHelper.addProject(projectModel)
        Log.e("asdf", " $id")

        for (projectMemberModel: ProjectMemberModel in projectMemberModels) {
            projectMemberModel.projectId = id
            dbHelper.addMember(projectMemberModel)
        }
        Handler().postDelayed({ finish() }, 300)
    }

    override fun onEditClick(position: Int) {
        id = projectMemberModels[position].id
        memberName = projectMemberModels[position].name
        memberEmail = projectMemberModels[position].email
        openAddMemberDialog()
    }

    override fun onDeleteClick(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.str_are_you_sure_to_want_to_delete)
        builder.setPositiveButton(R.string.str_yes) { dialog, _ ->
            dialog.dismiss()
            projectMemberModels.removeAt(position)
            projectMemberAdapter.notifyDataSetChanged()
        }
        builder.setNegativeButton(R.string.str_no) { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private var id: Int = 0
    private var memberName: String = ""
    private var memberEmail: String = ""
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var btnAddProjectMember: Button
    private lateinit var alertDialog: AlertDialog

    @SuppressLint("InflateParams")
    private fun openAddMemberDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_project_member, null)
        builder.setView(dialogView)

        editTextName = dialogView.findViewById(R.id.etName) as EditText
        editTextName.setHint(R.string.str_enter_member_name)
        editTextName.setText(memberName)

        editTextEmail = dialogView.findViewById(R.id.etEmailId) as EditText
        editTextEmail.setHint(R.string.str_enter_member_email_id)
        editTextEmail.setText(memberEmail)

        btnAddProjectMember = dialogView.findViewById(R.id.btnAddProjectMember) as Button
        if (id != 0)
            btnAddProjectMember.setText(R.string.str_update)
        else
            btnAddProjectMember.setText(R.string.str_add)

        alertDialog = builder.create()

        btnAddProjectMember.setOnClickListener {
            if (editTextName.text.toString().isEmpty()) {
                Toast.makeText(this@AddProjectActivity, "Enter Member Name", Toast.LENGTH_SHORT).show()
            } //else if (editTextEmail.text.toString().isEmpty()) {
            //Toast.makeText(this@AddProjectActivity, "Enter Member Email Id", Toast.LENGTH_SHORT).show()
            //} else if (ValidationHelper.isValidEmail(editTextEmail.text.toString())) {
            //Toast.makeText(this@AddProjectActivity, "Enter Valid Member Email Id", Toast.LENGTH_SHORT).show()
//            }
            else {
                alertDialog.dismiss()
                if (id != 0) {
                    Toast.makeText(this@AddProjectActivity, "updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AddProjectActivity, "added", Toast.LENGTH_SHORT).show()
                    projectMemberModels.add(ProjectMemberModel(editTextName.text.toString(), editTextEmail.text.toString()))
                    projectMemberAdapter.notifyDataSetChanged()
                }
            }
        }

        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
