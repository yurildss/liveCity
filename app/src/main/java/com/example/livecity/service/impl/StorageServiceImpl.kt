package com.example.livecity.service.impl

import com.example.livecity.model.Evaluation
import com.example.livecity.service.module.StorageService

class StorageServiceImpl: StorageService{
    override suspend fun saveAlert(alert: Evaluation) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlert(alert: Evaluation) {
        TODO("Not yet implemented")
    }

    override suspend fun getAlerts(): List<Evaluation> {
        TODO("Not yet implemented")
    }
}