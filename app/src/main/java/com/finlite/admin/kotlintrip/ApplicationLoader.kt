package com.finlite.admin.kotlintrip

import android.app.Application
import com.google.firebase.FirebaseApp

class ApplicationLoader : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    companion object {
        private var applicationLoader: ApplicationLoader? = null

        val instance: ApplicationLoader
            get() {
                if (applicationLoader == null) applicationLoader = ApplicationLoader()
                return applicationLoader!!
            }

//        private lateinit var applicationLoader: ApplicationLoader
//        @JvmStatic
//        fun getInstance(): ApplicationLoader {
//
//            return applicationLoader
//        }
    }

    private var projectId: Int = 0

    fun getProjectId(): Int {
        return projectId
    }

    fun setProjectId(projectId: Int) {
        this.projectId = projectId
    }

    private var expenseId: Int = 0

    fun getExpenseId(): Int {
        return expenseId
    }

    fun setExpenseId(expenseId: Int) {
        this.expenseId = expenseId
    }
}