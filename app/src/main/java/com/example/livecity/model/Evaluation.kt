package com.example.livecity.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

data class Evaluation(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: com.google.firebase.Timestamp? = null,
    val type: Type = Type("", ""),
    val position: GeoPoint? = null,
    val userId: String = "",
    val dateClose: com.google.firebase.Timestamp? = null,
    val closed: Boolean = false,
    val formattedAddress: String = ""
){
    val formatDate = date?.toDate()?.let { date ->
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(date)
    } ?: ""
}