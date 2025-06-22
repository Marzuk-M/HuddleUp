package com.example.huddleup.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /** Register a new account and create `/users/{uid}`. */
    suspend fun signUp(email: String, password: String): Result<FirebaseUser> =
        runCatching {
            // 1️⃣ create auth user
            auth.createUserWithEmailAndPassword(email, password).await()

            // 2️⃣ fetch the new user object
            val user = auth.currentUser ?: error("No user returned by Firebase")

            // 3️⃣ create profile doc
            db.collection("users")
                .document(user.uid)
                .set(
                    mapOf(
                        "email"     to email,
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                )
                .await()

            // 4️⃣ return it as the lambda result
            user
        }

    /** Log in an existing account. */
    suspend fun login(email: String, password: String): Result<FirebaseUser> =
        runCatching {
            auth.signInWithEmailAndPassword(email, password).await()
            auth.currentUser ?: error("No user after login")
        }

    /** Sign out locally. */
    fun logout() = auth.signOut()

    /** Currently-signed-in user or null. */
    val currentUser: FirebaseUser? get() = auth.currentUser
}
