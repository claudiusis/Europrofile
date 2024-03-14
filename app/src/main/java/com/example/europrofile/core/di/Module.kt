package com.example.europrofile.core.di

import com.example.europrofile.data.AuthRepositoryImpl
import com.example.europrofile.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@Module
@DisableInstallInCheck
abstract class Module {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}