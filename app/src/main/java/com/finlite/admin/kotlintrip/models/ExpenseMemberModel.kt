package com.finlite.admin.kotlintrip.models

data class ExpenseMemberModel(var name: String) {

    var id: Int = 0
    var check: Boolean = false
    var price: Float = 0F
    var userExpenseId: Int = 0

    init {
        this.id = 0
        this.check = true
        this.price = 0F
        this.userExpenseId = 0
    }
}
