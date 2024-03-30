package com.example.europrofile.core.di

import com.example.europrofile.data.AccountRepositoryImpl
import com.example.europrofile.data.AuthRepositoryImpl
import com.example.europrofile.domain.AccountRepository
import com.example.europrofile.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class Module {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideAccountRepository(accountRepositoryImpl: AccountRepositoryImpl) : AccountRepository
}