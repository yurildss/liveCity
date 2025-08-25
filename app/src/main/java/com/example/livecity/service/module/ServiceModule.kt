package com.example.livecity.service.module

import com.example.livecity.service.AccountService
import com.example.livecity.service.impl.AccountServiceImpl
import com.example.livecity.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

}