package com.finlite.admin.kotlintrip.models

data class ProjectMemberModel(var name: String, var email: String) {

    var id: Int = 0
    var projectId: Int = 0

    init {
        this.id = 0
        this.projectId = 0
    }
}
