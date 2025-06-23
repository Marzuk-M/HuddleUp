package com.example.huddleup

// Navigation Related Utility
object NavigationUtils {

    // check if for given route, bottom navbar should be hidden
    fun checkIfDisabledNavBarRoute(route: String): Boolean {
        if (route == Routes.LOGIN) return false
        if (route == Routes.SIGNUP) return false
        return true // TODO: ADD LOGIC TO DISABLE THE NAVBAR FOR CERTAIN SCREENS
    }

    // check if user is logged in
    fun isUserLoggedIn(): Boolean {
        return false // TODO: CHECK IF USER IS LOGGED IN
    }
}