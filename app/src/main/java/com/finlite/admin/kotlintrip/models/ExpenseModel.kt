package com.finlite.admin.kotlintrip.models

data class ExpenseModel(var description: String, var expensePrice: String) {

    var id: Int = 0
    var memberId: Int = 0
    var projectId: Int = 0

    init {
        this.id = 0
        this.memberId = 0
        this.projectId = 0
    }
}
