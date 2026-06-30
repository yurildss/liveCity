package com.example.livecity.screens.login

import com.example.livecity.service.AccountService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LoginScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var accountService: AccountService
    private lateinit var viewModel: LoginScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        accountService = mockk()
        viewModel = LoginScreenViewModel(accountService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isCorrect() = runBlocking {
        val state = viewModel.state.first()
        assertEquals("", state.email)
        assertEquals("", state.password)
        assertEquals("", state.message)
    }

    @Test
    fun onEmailChange_updatesEmail() = runBlocking {
        val testEmail = "test@example.com"
        viewModel.onEmailChange(testEmail)

        val state = viewModel.state.first()
        assertEquals(testEmail, state.email)
    }

    @Test
    fun onPasswordChange_updatesPassword() = runBlocking {
        val testPassword = "password123"
        viewModel.onPasswordChange(testPassword)

        val state = viewModel.state.first()
        assertEquals(testPassword, state.password)
    }

    @Test
    fun clearMessage_clearsMessage() = runBlocking {
        viewModel.onPasswordChange("pass")
        viewModel.onEmailChange("email@test.com")
        
        // Note: We verify clearMessage functionality
        viewModel.clearMessage()
        val state = viewModel.state.first()
        assertEquals("", state.message)
    }

    @Test
    fun onLoginClick_withEmptyEmail_showsError() = runBlocking {
        viewModel.onPasswordChange("password123")

        var loginSuccessful = false
        viewModel.onLoginClick { loginSuccessful = true }

        val state = viewModel.state.first()
        assertEquals("Email cannot be empty", state.message)
        assertEquals(false, loginSuccessful)
    }

    @Test
    fun onLoginClick_withEmptyPassword_showsError() = runBlocking {
        viewModel.onEmailChange("test@example.com")

        var loginSuccessful = false
        viewModel.onLoginClick { loginSuccessful = true }

        val state = viewModel.state.first()
        assertEquals("Password cannot be empty", state.message)
        assertEquals(false, loginSuccessful)
    }

    @Test
    fun onLoginClick_withValidCredentials_authenticates() = runBlocking {
        val testEmail = "test@example.com"
        val testPassword = "password123"

        coEvery { accountService.authenticate(testEmail, testPassword) } returns "Success"

        viewModel.onEmailChange(testEmail)
        viewModel.onPasswordChange(testPassword)

        var onSuccessfulLoginCalled = false
        viewModel.onLoginClick { onSuccessfulLoginCalled = true }
        
        // Give async operation time to complete
        kotlinx.coroutines.delay(100)

        val state = viewModel.state.first()
        assertEquals(true, onSuccessfulLoginCalled)
    }

    @Test
    fun onLoginClick_withInvalidCredentials_showsError() = runBlocking {
        val testEmail = "test@example.com"
        val testPassword = "wrongpassword"
        val errorMessage = "Invalid email or password"

        coEvery { accountService.authenticate(testEmail, testPassword) } returns errorMessage

        viewModel.onEmailChange(testEmail)
        viewModel.onPasswordChange(testPassword)

        var onSuccessfulLoginCalled = false
        viewModel.onLoginClick { onSuccessfulLoginCalled = true }
        
        // Give async operation time to complete
        kotlinx.coroutines.delay(100)

        val state = viewModel.state.first()
        assertEquals(false, onSuccessfulLoginCalled)
        assertEquals(errorMessage, state.message)
    }
}

