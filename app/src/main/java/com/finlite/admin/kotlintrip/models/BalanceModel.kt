package com.finlite.admin.kotlintrip.models

class BalanceModel {
    var memberId: Int = 0
    var memberName: String = ""
    var memberTotalPaid: Float = 0F
    var divisionPaid: Float = 0F
    var balance: Float = 0F

    init {
        memberId = 0
        memberName = ""
        memberTotalPaid = 0F
        divisionPaid = 0F
        balance = 0F
    }
}