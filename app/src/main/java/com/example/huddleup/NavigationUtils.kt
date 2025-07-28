package com.example.huddleup

import com.google.firebase.auth.FirebaseAuth

// Navigation Related Utility
object NavigationUtils {

    // check if for given route, bottom navbar should be hidden
    fun checkIfDisabledNavBarRoute(route: String): Boolean {
        if (route == Routes.LOGIN) return false
        if (route == Routes.SIGNUP) return false
        if (route.startsWith("chat/")) return false
        if (route.startsWith("team_details/")) return false
        return true // TODO: ADD LOGIC TO DISABLE THE NAVBAR FOR CERTAIN SCREENS
    }

    // check if user is logged in
    fun isUserLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
}