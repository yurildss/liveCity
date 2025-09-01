package com.example.livecity.service.impl

import com.example.livecity.model.Evaluation
import com.example.livecity.service.module.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): StorageService{
    override suspend fun saveAlert(alert: Evaluation) {
        firestore.collection("alerts").add(alert).await()
    }

    override suspend fun deleteAlert(alert: Evaluation) {
        TODO("Not yet implemented")
    }

    override suspend fun getAlerts(): List<Evaluation> {
        val allAlerts = firestore.collection("alerts").get().await()
        return allAlerts.toObjects(Evaluation::class.java)
    }

    override suspend fun getAlertsById(id: String): Evaluation? {
        TODO("Not yet implemented")
    }

    override suspend fun getAlertsByUser(userId: String): List<Evaluation?> {
        val alerts = firestore.collection("alerts").whereEqualTo("userId", userId).get().await()
        return alerts.toObjects(Evaluation::class.java)
    }
}