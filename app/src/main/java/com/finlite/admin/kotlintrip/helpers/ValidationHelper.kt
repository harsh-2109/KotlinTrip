package com.finlite.admin.kotlintrip.helpers

object ValidationHelper {

    private val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    fun isValidEmail(email: String): Boolean {
        return !email.matches(EMAIL_PATTERN.toRegex())
    }

    fun isValidMobileNumber(mobile: String): Boolean {
        return mobile.length != 10
    }

    fun isValidPhoneNumber(mobile: String): Boolean {
        return mobile.length != 8
    }
}
