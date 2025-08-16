package com.example.livecity.service

interface AccountService {
    val currentUserId: String

    suspend fun register(email: String, password: String, name: String)
    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()

}