package com.example.livecity.service.impl

import com.example.livecity.service.AccountService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ) {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user

        val profileUpdates = userProfileChangeRequest{
            displayName = name
        }

        user!!.updateProfile(profileUpdates).await()
    }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount() {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}