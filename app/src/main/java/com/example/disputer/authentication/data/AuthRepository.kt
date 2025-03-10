package com.example.disputer.authentication.data

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface AuthRepository {

    suspend fun login(email: String, password: String)

    suspend fun register(
        email: String,
        password: String,
        repeatPassword: String = ""
    )

    fun logout()



    class Base(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore
    ) : AuthRepository {

        override suspend fun register(email: String, password: String, repeatPassword : String) {
            return suspendCoroutine<Unit> { continuation ->
                try {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { authResult ->
                            val user = authResult.user
                            user?.sendEmailVerification()?.addOnSuccessListener {

                                // Сохранение пользователя в Firestore
                                val newUser = User(
                                    uid = user.uid,
                                    email = user.email ?: "",
                                    isAdmin = false // По умолчанию false
                                )

                                firestore.collection("users").document(user.uid).set(newUser)
                                    .addOnSuccessListener {
                                        continuation.resume(Unit)
                                    }
                                    .addOnFailureListener { e ->
                                        continuation.resumeWithException(e)
                                    }
                            }?.addOnFailureListener { e ->
                                continuation.resumeWithException(e)
                            }
                        }
                        .addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    continuation.resumeWithException(e)
                }
            }
        }

        override suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
            try {
                suspendCoroutine<Unit> { continuation ->
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { authResult ->
                            val user = authResult.user
                            if (user != null && user.isEmailVerified) {
                                continuation.resume(Unit)
                            } else {
                                continuation.resumeWithException(Exception("Email not verified"))
                            }
                        }
                        .addOnFailureListener {
                            continuation.resumeWithException(Exception("Email or password is incorrect"))
                        }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                throw e
            }
        }

        override fun logout() {
            firebaseAuth.signOut()
        }
    }


    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.contains("@") && email.substringAfter("@").contains(".")
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6 && password.any { !it.isLetterOrDigit() }
    }
}
