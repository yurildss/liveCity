package com.example.livecity.service.module

import com.example.livecity.model.Evaluation

interface StorageService {

    suspend fun saveAlert(alert: Evaluation)
    suspend fun deleteAlert(alert: Evaluation)
    suspend fun getAlerts(): List<Evaluation>
    suspend fun getAlertsById(id: String): Evaluation?
    suspend fun getAlertsByUser(userId: String): List<Evaluation?>

}