package com.example.huddleup.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.huddleup.Endpoints
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

@Serializable
data class SignupRequestDTO(
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val username: String,
    val date_of_birth: String
)

object AuthService {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun getFirebaseAuthToken(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val user = auth.currentUser ?: throw Exception("User not logged in")
                val tokenResult = user.getIdToken(true).await()
                tokenResult.token // Return the token
            } catch (e: Exception) {
                Log.e("AuthService", "Error retrieving Firebase token", e)
                null
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                        if (tokenTask.isSuccessful) {
                            println("token: ${tokenTask.result?.token}") // Print the Firebase JWT
                            Log.d("FirebaseCheck", "Auth successful, user: ${user.uid}")
                            onSuccess()
                        } else {
                            Log.e("FirebaseCheck", "Token retrieval failed", tokenTask.exception)
                            onFailure(tokenTask.exception)
                        }
                    }
                } else {
                    Log.e("FirebaseCheck", "Auth failed", task.exception)
                    onFailure(task.exception)
                }
            }
    }

    fun logout(onSuccess: () -> Unit) {
        auth.signOut()
        onSuccess()
    }

    suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        username: String,
        dateOfBirth: String,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val requestBody = SignupRequestDTO(
                    email = email,
                    password = password,
                    first_name = firstName,
                    last_name = lastName,
                    username = username,
                    date_of_birth = dateOfBirth
                )

                val jsonRequestBody = Json.encodeToString(requestBody)

                val url = URL(Endpoints.getSignupEndpoint())
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    doOutput = true
                    setRequestProperty("Content-Type", "application/json")
                    outputStream.write(jsonRequestBody.toByteArray(Charsets.UTF_8))
                }

                val responseCode = connection.responseCode
                if (responseCode in 200..299) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Signup Successful!", Toast.LENGTH_LONG).show()
                        onSuccess()
                    }
                } else {
                    throw Exception("API signup failed with code: $responseCode")
                }
            } catch (e: Exception) {
                Log.e("SignUpError", "Error signing up user", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Signup Failed!", Toast.LENGTH_SHORT).show()
                    onFailure(e)
                }
            }
        }
    }

    suspend fun checkUsername(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val apiUrl = Endpoints.checkUsernameEndpoint(username)
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                val isAvailable = responseCode == 200 // Assume 200 means available, 409 means taken

                Log.d("DEBUG", "isAvailable: $isAvailable")
                isAvailable
            } catch (e: Exception) {
                Log.e("DEBUG", "Error checking public handle", e)
                false
            }
        }
    }
}