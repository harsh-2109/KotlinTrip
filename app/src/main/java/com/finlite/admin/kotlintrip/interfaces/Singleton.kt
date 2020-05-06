package com.finlite.admin.kotlintrip.interfaces

object Singleton {

    private var instance: Singleton? = null

    fun getInstance(): Singleton {
        if (instance == null) {
            instance = Singleton
        }

        return instance as Singleton
    }
}