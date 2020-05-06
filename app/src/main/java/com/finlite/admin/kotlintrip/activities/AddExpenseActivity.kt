package com.finlite.admin.kotlintrip.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.finlite.admin.kotlintrip.ApplicationLoader
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.adapters.ExpenseMemberAdapter
import com.finlite.admin.kotlintrip.database.DBHelper
import com.finlite.admin.kotlintrip.models.ExpenseMemberModel
import com.finlite.admin.kotlintrip.models.ExpenseModel
import com.finlite.admin.kotlintrip.models.UserExpenseModel
import com.finlite.admin.kotlintrip.utils.EqualSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_add_expense.*
import kotlinx.android.synthetic.main.toolbar_main.*

class AddExpenseActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var expenseMemberModels: List<ExpenseMemberModel>
    private lateinit var expenseMemberAdapter: ExpenseMemberAdapter

    private lateinit var paidExpenseMemberModels: List<ExpenseMemberModel>

    private lateinit var dbHelper: DBHelper

    private var memberId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        memberId = paidExpenseMemberModels.get(position).id
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun init() {
        dbHelper = DBHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(EqualSpacingItemDecoration(10))
        paidExpenseMemberModels = dbHelper.getAllProjectExpenseMembers(ApplicationLoader.instance.getProjectId())

        var members = ArrayList<String>()
        memberId = paidExpenseMemberModels[0].id
        for (expense: ExpenseMemberModel in paidExpenseMemberModels)
            members.add(expense.name)

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, members)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(aa)
        spinner!!.onItemSelectedListener = this

        expenseMemberModels = dbHelper.getAllProjectExpenseMembers(ApplicationLoader.instance.getProjectId())

        expenseMemberAdapter = ExpenseMemberAdapter(this, expenseMemberModels)
        recyclerView.adapter = expenseMemberAdapter

        etExpense.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etExpense.text.toString().isEmpty())
                    expenseMemberAdapter.addPrice(0)
                else
                    expenseMemberAdapter.addPrice(Integer.parseInt(etExpense.text.toString()))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        tvCheckAll.setOnClickListener {
            if (tvCheckAll.text.toString().equals("check", true)) {
                for (expense: ExpenseMemberModel in expenseMemberModels) {
                    expense.check = false
                }
                tvCheckAll.text = "uncheck"
            } else {
                for (expense: ExpenseMemberModel in expenseMemberModels) {
                    expense.check = true
                }
                tvCheckAll.text = "check"
            }
            expenseMemberAdapter.refresh(etExpense.text.toString().toInt())
        }

        btnAddExpense.setOnClickListener {
            if (btnAddExpense.text.toString().equals("update expense")) {
                if (etProjectDescription.text.toString().isEmpty()) {
                    Toast.makeText(this, "Enter Expense Description", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (etExpense.text.toString().isEmpty()
                        || etExpense.text.toString().toInt() == 0) {
                    Toast.makeText(this, "Enter Expense Price", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                var count = 0
                for (expense: ExpenseMemberModel in expenseMemberModels) {
                    if (expense.check)
                        count++
                }
                if (count == 0) {
                    Toast.makeText(this, "Add expense to member", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val expenseModel = ExpenseModel(etProjectDescription.text.toString(), etExpense.text.toString())
                expenseModel.id = this.expenseModel.id
                expenseModel.memberId = memberId
                expenseModel.projectId = ApplicationLoader.instance.getProjectId()
                dbHelper.updateExpense(expenseModel)

                for (expense: ExpenseMemberModel in expenseMemberModels) {
                    val userExpenseModel = UserExpenseModel(this.expenseModel.id, expense.id,
                            ApplicationLoader.instance.getProjectId(), expense.price.toString())
                    userExpenseModel.id = expense.userExpenseId
                    dbHelper.updateUserExpense(userExpenseModel)
//                    Log.e("expennnnnnn", "" + userExpenseModel.id + "," + userExpenseModel.expensePrice + ","
//                            + userExpenseModel.expenseId + "," + userExpenseModel.memberId + "," + userExpenseModel.projectId)
                }
                Handler().postDelayed({ finish() }, 300)
            } else {
                if (etProjectDescription.text.toString().isEmpty()) {
                    Toast.makeText(this, "Enter Expense Description", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (etExpense.text.toString().isEmpty()
                        || etExpense.text.toString().toInt() == 0) {
                    Toast.makeText(this, "Enter Expense Price", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                var count = 0
                for (expense: ExpenseMemberModel in expenseMemberModels) {
                    if (expense.check)
                        count++
                }
                if (count == 0) {
                    Toast.makeText(this, "Add expense to member", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val expenseModel = ExpenseModel(etProjectDescription.text.toString(), etExpense.text.toString())
                expenseModel.memberId = memberId
                expenseModel.projectId = ApplicationLoader.instance.getProjectId()
                val id = dbHelper.addExpense(expenseModel)

                for (expense: ExpenseMemberModel in expenseMemberModels) {
                    val userExpenseModel = UserExpenseModel(id, expense.id, ApplicationLoader.instance.getProjectId(),
                            expense.price.toString())
                    dbHelper.addUserExpense(userExpenseModel)
                }
                Handler().postDelayed({ finish() }, 300)
            }
        }
        btnAddExpense.setText("add expense")
        if (ApplicationLoader.instance.getExpenseId() != 0) {
            var expenseId = ApplicationLoader.instance.getExpenseId()
            Log.e("expense", "" + expenseId)

            expenseModel = dbHelper.getExpenseDetail(expenseId)
            etProjectDescription.setText(expenseModel.description)
            etExpense.setText(expenseModel.expensePrice)

            var position = 0

            for (i: Int in paidExpenseMemberModels.indices)
                if (paidExpenseMemberModels.get(i).id == expenseModel.memberId) {
                    memberId = expenseModel.memberId
                    position = i
                }

            Log.e("memberdd", "" + position)
            spinner.setSelection(position)

            var userExpenses = dbHelper.getAllUserExpenses(expenseModel.id)

            for (exps: UserExpenseModel in userExpenses) {
                for (member: ExpenseMemberModel in expenseMemberModels) {
                    if (member.id == exps.memberId) {
                        member.userExpenseId = exps.id
                        member.price = exps.expensePrice.toFloat()
                        member.check = !exps.expensePrice.equals("0.0")
                    }
                }
            }
            expenseMemberAdapter.refresh(etExpense.text.toString().toInt())
            btnAddExpense.setText("update expense")
        }
    }

    private lateinit var expenseModel: ExpenseModel

    fun setCheckAll(check: Boolean) {
        if (check)
            tvCheckAll.setText("check")
        else
            tvCheckAll.setText("uncheck")
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
