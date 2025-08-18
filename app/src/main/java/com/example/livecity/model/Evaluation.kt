package com.example.livecity.model

import java.time.LocalDate

data class Evaluation(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDate,
    val type: Pair<String, Int>,
    val position: Pair<Double, Double>,
    val userId: String,
    val dateClose: LocalDate,
    val closed: Boolean = false
)