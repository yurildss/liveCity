package com.example.livecity.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class UserModelTest {

    @Test
    fun userCreation_withAllParameters_succeeds() {
        val user = User(
            id = "user123",
            name = "John Doe",
            email = "john@example.com",
            password = "password123"
        )

        assertEquals("user123", user.id)
        assertEquals("John Doe", user.name)
        assertEquals("john@example.com", user.email)
        assertEquals("password123", user.password)
    }

    @Test
    fun user_isMutableDataClass() {
        val user1 = User(
            id = "user1",
            name = "John",
            email = "john@test.com",
            password = "pass1"
        )

        val user2 = user1.copy(name = "Jane")

        assertEquals("John", user1.name)
        assertEquals("Jane", user2.name)
        assertEquals("user1", user2.id)
    }

    @Test
    fun user_equalsAndHashCode_work() {
        val user1 = User(
            id = "user1",
            name = "John",
            email = "john@test.com",
            password = "pass1"
        )

        val user2 = User(
            id = "user1",
            name = "John",
            email = "john@test.com",
            password = "pass1"
        )

        val user3 = User(
            id = "user2",
            name = "Jane",
            email = "jane@test.com",
            password = "pass2"
        )

        assertEquals(user1, user2)
        assertEquals(user1.hashCode(), user2.hashCode())
        assertNotEquals(user1, user3)
    }

    @Test
    fun user_toStringWorks() {
        val user = User("user1", "John Doe", "john@test.com", "pass123")
        val userString = user.toString()

        assert(userString.contains("John Doe"))
        assert(userString.contains("john@test.com"))
    }

    @Test
    fun user_withDifferentEmails_notEqual() {
        val user1 = User("user1", "John", "john@test.com", "pass1")
        val user2 = User("user1", "John", "john2@test.com", "pass1")

        assertNotEquals(user1, user2)
    }

    @Test
    fun user_withDifferentPasswords_notEqual() {
        val user1 = User("user1", "John", "john@test.com", "pass1")
        val user2 = User("user1", "John", "john@test.com", "pass2")

        assertNotEquals(user1, user2)
    }
}

