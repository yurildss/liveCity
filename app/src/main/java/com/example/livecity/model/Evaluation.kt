package com.example.livecity.model

import com.google.firebase.firestore.GeoPoint
import java.time.LocalDate
import java.util.Date

data class Evaluation(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: com.google.firebase.Timestamp? = null,
    val type: Type = Type("", 0),
    val position: GeoPoint? = null,
    val userId: String = "",
    val dateClose: com.google.firebase.Timestamp? = null,
    val closed: Boolean = false
)