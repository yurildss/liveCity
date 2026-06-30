package com.example.livecity.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import org.junit.Test
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class EvaluationModelTest {

    @Test
    fun evaluationCreation_withAllParameters_succeeds() {
        val geoPoint = GeoPoint(-12.9714, -38.5014)
        val timestamp = Timestamp(Date())
        val type = Type("Danger", "ic_danger")

        val evaluation = Evaluation(
            id = "test123",
            title = "Test Alert",
            description = "Test Description",
            date = timestamp,
            type = type,
            position = geoPoint,
            userId = "user123",
            dateClose = null,
            closed = false,
            formattedAddress = "Test Address, 123"
        )

        assertEquals("test123", evaluation.id)
        assertEquals("Test Alert", evaluation.title)
        assertEquals("Test Description", evaluation.description)
        assertEquals(timestamp, evaluation.date)
        assertEquals(type, evaluation.type)
        assertEquals(geoPoint, evaluation.position)
        assertEquals("user123", evaluation.userId)
        assertEquals(null, evaluation.dateClose)
        assertEquals(false, evaluation.closed)
        assertEquals("Test Address, 123", evaluation.formattedAddress)
    }

    @Test
    fun evaluationCreation_withDefaults_succeeds() {
        val evaluation = Evaluation()

        assertEquals("", evaluation.id)
        assertEquals("", evaluation.title)
        assertEquals("", evaluation.description)
        assertEquals(null, evaluation.date)
        assertEquals(Type("", ""), evaluation.type)
        assertEquals(null, evaluation.position)
        assertEquals("", evaluation.userId)
        assertEquals(null, evaluation.dateClose)
        assertEquals(false, evaluation.closed)
        assertEquals("", evaluation.formattedAddress)
    }

    @Test
    fun evaluationFormatDate_withValidTimestamp_formatsCorrectly() {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(2024, 0, 15) // January 15, 2024
        val date = calendar.time
        val timestamp = Timestamp(date)

        val evaluation = Evaluation(
            title = "Test",
            date = timestamp,
            position = GeoPoint(0.0, 0.0),
            type = Type("", "")
        )

        assertNotNull(evaluation.formatDate)
        // Format should be dd/MM/yyyy
        assertEquals("15/01/2024", evaluation.formatDate)
    }

    @Test
    fun evaluationFormatDate_withNullTimestamp_returnsEmptyString() {
        val evaluation = Evaluation(
            title = "Test",
            date = null,
            position = GeoPoint(0.0, 0.0),
            type = Type("", "")
        )

        assertEquals("", evaluation.formatDate)
    }

    @Test
    fun evaluation_isMutableDataClass() {
        val evaluation1 = Evaluation(
            id = "1",
            title = "Alert 1",
            position = GeoPoint(-12.9714, -38.5014),
            type = Type("Danger", "ic_danger")
        )

        val evaluation2 = evaluation1.copy(
            id = "2",
            title = "Alert 2"
        )

        assertEquals("1", evaluation1.id)
        assertEquals("Alert 1", evaluation1.title)
        assertEquals("2", evaluation2.id)
        assertEquals("Alert 2", evaluation2.title)
    }

    @Test
    fun evaluation_equalsAndHashCode_work() {
        val type = Type("Danger", "ic_danger")
        val position = GeoPoint(-12.9714, -38.5014)

        val evaluation1 = Evaluation(
            id = "1",
            title = "Alert",
            type = type,
            position = position
        )

        val evaluation2 = Evaluation(
            id = "1",
            title = "Alert",
            type = type,
            position = position
        )

        assertEquals(evaluation1, evaluation2)
        assertEquals(evaluation1.hashCode(), evaluation2.hashCode())
    }
}

