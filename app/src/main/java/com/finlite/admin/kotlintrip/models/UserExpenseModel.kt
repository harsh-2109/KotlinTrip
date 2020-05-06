package com.finlite.admin.kotlintrip.models

data class UserExpenseModel(var expenseId: Int, var memberId: Int, var projectId: Int, var expensePrice: String) {

    var id: Int = 0

    init {
        this.id = 0
    }
}
