package com.finlite.admin.kotlintrip.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.finlite.admin.kotlintrip.ApplicationLoader
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.adapters.ExpenseAdapter
import com.finlite.admin.kotlintrip.database.DBHelper
import com.finlite.admin.kotlintrip.interfaces.OnItemClickListener
import com.finlite.admin.kotlintrip.models.ExpenseModel
import com.finlite.admin.kotlintrip.utils.EqualSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_project_details.*
import kotlinx.android.synthetic.main.toolbar_main.*

class ProjectDetailsActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var expenseModels: List<ExpenseModel>
    private lateinit var expenseAdapter: ExpenseAdapter

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.e("dddd", "" + ApplicationLoader.instance.getProjectId())
        init()
    }

    private fun init() {
        title = "Kulu Manali"

        dbHelper = DBHelper(this)

        dbHelper.getAllProjectMembers(ApplicationLoader.instance.getProjectId())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(EqualSpacingItemDecoration(10))

        btnBalance.setOnClickListener { startActivity(Intent(this, BalanceActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        setList()
    }

    private fun setList() {
        expenseModels = dbHelper.getAllProjectExpenses(ApplicationLoader.instance.getProjectId())

        expenseAdapter = ExpenseAdapter(this, expenseModels, this)
        recyclerView.adapter = expenseAdapter
    }

    override fun onClick(position: Int) {
        ApplicationLoader.instance.setExpenseId(expenseModels.get(position).id)
        startActivity(Intent(this@ProjectDetailsActivity, AddExpenseActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_project_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.menu_add_expense -> {
                ApplicationLoader.instance.setExpenseId(0)
                startActivity(Intent(this, AddExpenseActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
