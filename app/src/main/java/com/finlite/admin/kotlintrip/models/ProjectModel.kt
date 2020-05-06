package com.finlite.admin.kotlintrip.models

data class ProjectModel(var name: String, var description: String) {

    var id: Int = 0
    var totalMember: Int = 0
    var totalExpenses: Int = 0

    init {
        this.id = 0
        this.totalMember = 0
        this.totalExpenses = 0
    }
}
