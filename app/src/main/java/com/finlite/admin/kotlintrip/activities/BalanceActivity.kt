package com.finlite.admin.kotlintrip.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.finlite.admin.kotlintrip.ApplicationLoader
import com.finlite.admin.kotlintrip.R
import com.finlite.admin.kotlintrip.adapters.BalanceAdapter
import com.finlite.admin.kotlintrip.adapters.SettleAdapter
import com.finlite.admin.kotlintrip.database.DBHelper
import com.finlite.admin.kotlintrip.models.BalanceModel
import com.finlite.admin.kotlintrip.models.ExpenseMemberModel
import com.finlite.admin.kotlintrip.models.ExpenseModel
import com.finlite.admin.kotlintrip.models.SettleModel
import kotlinx.android.synthetic.main.activity_balance.*
import kotlinx.android.synthetic.main.toolbar_main.*

class BalanceActivity : AppCompatActivity() {

    private lateinit var balanceModels: List<BalanceModel>
    private lateinit var balanceAdapter: BalanceAdapter

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init() {
        dbHelper = DBHelper(this)

        var totalExpenses = dbHelper.getTotalExpenses(+ApplicationLoader.instance.getProjectId())
//        Log.e("total", "" + totalExpenses)

        var members = dbHelper.getAllProjectExpenseMembers(ApplicationLoader.instance.getProjectId())

        var expenses = dbHelper.getAllProjectExpenses(ApplicationLoader.instance.getProjectId())
//        Log.e("expenselength", "" + expenses.size)

        var userExpenses = dbHelper.getAllUserExpensesProjectId(ApplicationLoader.instance.getProjectId())
//        Log.e("userexpenselength", "" + userExpenses.size)

        // who pay how much money

        var balanceModels: ArrayList<BalanceModel> = ArrayList()

        for (em: ExpenseMemberModel in members) {
            var balanceModel = BalanceModel()
            balanceModel.memberId = em.id
            balanceModel.memberName = em.name
            for (e: ExpenseModel in expenses) {
                if (em.id == e.memberId) {
                    balanceModel.memberTotalPaid += e.expensePrice.toFloat()
                }
            }

            balanceModel.divisionPaid = dbHelper
                    .getAllUserExpensesMemberId(balanceModel.memberId, ApplicationLoader.instance.getProjectId())
            balanceModel.balance = balanceModel.memberTotalPaid - balanceModel.divisionPaid
//            Log.e("total ex " + em.name, "" + balanceModel.memberId + "," + balanceModel.memberName
//                    + "," + balanceModel.memberTotalPaid + "," + balanceModel.divisionPaid)
            balanceModels.add(balanceModel)
        }



        recyclerViewBalance.layoutManager = LinearLayoutManager(this)
        balanceAdapter = BalanceAdapter(this, balanceModels, null)
        recyclerViewBalance.adapter = balanceAdapter


        var plusList: ArrayList<BalanceModel> = ArrayList()
        var minusList: ArrayList<BalanceModel> = ArrayList()

        for (i in balanceModels.indices) {
            if ((balanceModels[i].memberTotalPaid - balanceModels[i].divisionPaid) < 0)
                minusList.add(balanceModels[i])
            else
                plusList.add(balanceModels[i])
        }

        Log.e("plus", "plus")
        for (bal: BalanceModel in plusList) {
            Log.e("total ex ", "" + bal.memberId + "," + bal.memberName
                    + "," + bal.memberTotalPaid + "," + bal.divisionPaid + "," + bal.balance)
        }

        Log.e("minus", "minus")
        for (bal: BalanceModel in minusList) {
            Log.e("total ex ", "" + bal.memberId + "," + bal.memberName
                    + "," + bal.memberTotalPaid + "," + bal.divisionPaid + "," + bal.balance)
        }
        Log.e("123----------", "----------------")
        var settleModels = ArrayList<SettleModel>()

        while (plusList.size != 0 && minusList.size != 0) {
            if (plusList[0].balance == Math.abs(minusList[0].balance)) {
                settleModels.add(SettleModel(minusList[0].memberName, plusList[0].memberName, plusList[0].balance))

                plusList.removeAt(0)
                minusList.removeAt(0)
            } else if (plusList[0].balance > Math.abs(minusList[0].balance)) {
                settleModels.add(SettleModel(minusList[0].memberName, plusList[0].memberName, Math.abs(minusList[0].balance)))

                plusList[0].balance -= Math.abs(minusList[0].balance)
//                settleModels.add(SettleModel())
                minusList.removeAt(0)
                if (plusList[0].balance == 0F)
                    plusList.removeAt(0)
            } else {
                settleModels.add(SettleModel(minusList[0].memberName, plusList[0].memberName, plusList[0].balance))
                minusList[0].balance = -(Math.abs(minusList[0].balance) - plusList[0].balance)
                plusList.removeAt(0)
                if (minusList[0].balance == 0F)
                    minusList.removeAt(0)
            }

            Log.e("--------------", "------------------")

//            Log.e("plus", "plus")
//            for (bal: BalanceModel in plusList) {
//                Log.e("total ex ", "" + bal.memberId + "," + bal.memberName
//                        + "," + bal.memberTotalPaid + "," + bal.divisionPaid + "," + bal.balance)
//            }
//
//            Log.e("minus", "minus")
//            for (bal: BalanceModel in minusList) {
//                Log.e("total ex ", "" + bal.memberId + "," + bal.memberName
//                        + "," + bal.memberTotalPaid + "," + bal.divisionPaid + "," + bal.balance)
//            }

        }

        Log.e("settle", "settle")
        for (settle: SettleModel in settleModels) {
            Log.e("total ex ", "" + settle.from + "," + settle.to
                    + "," + settle.balalnce)
        }

        recyclerViewBalance.layoutManager = LinearLayoutManager(this)
        var settleAdapter = SettleAdapter(this, settleModels, null)
        recyclerViewBalance.adapter = settleAdapter
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
