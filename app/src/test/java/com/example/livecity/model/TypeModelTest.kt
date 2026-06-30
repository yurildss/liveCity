package com.example.livecity.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TypeModelTest {

    @Test
    fun typeCreation_withParameters_succeeds() {
        val type = Type(
            alertType = "Danger",
            alertImage = "ic_danger"
        )

        assertEquals("Danger", type.alertType)
        assertEquals("ic_danger", type.alertImage)
    }

    @Test
    fun typeCreation_withDefaults_succeeds() {
        val type = Type()

        assertEquals("", type.alertType)
        assertEquals("", type.alertImage)
    }

    @Test
    fun type_isMutableDataClass() {
        val type1 = Type("Car Crash", "ic_car_crash")
        val type2 = type1.copy(alertType = "Building")

        assertEquals("Car Crash", type1.alertType)
        assertEquals("Building", type2.alertType)
        assertEquals("ic_car_crash", type2.alertImage)
    }

    @Test
    fun type_equalsAndHashCode_work() {
        val type1 = Type("Danger", "ic_danger")
        val type2 = Type("Danger", "ic_danger")
        val type3 = Type("Building", "ic_building")

        assertEquals(type1, type2)
        assertEquals(type1.hashCode(), type2.hashCode())
        assertNotEquals(type1, type3)
    }

    @Test
    fun type_toStringWorks() {
        val type = Type("Danger", "ic_danger")
        val typeString = type.toString()

        assert(typeString.contains("Danger"))
        assert(typeString.contains("ic_danger"))
    }

    @Test
    fun type_constructor_withNamedParameters() {
        val type = Type(alertType = "Building", alertImage = "ic_building")

        assertEquals("Building", type.alertType)
        assertEquals("ic_building", type.alertImage)
    }
}

