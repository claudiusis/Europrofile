package com.example.europrofile.core.di

import com.example.europrofile.data.AccountRepositoryImpl
import com.example.europrofile.data.AuthRepositoryImpl
import com.example.europrofile.data.ConditionInfoImpl
import com.example.europrofile.data.OrderRepositoryImpl
import com.example.europrofile.data.ReviewRepositoryImpl
import com.example.europrofile.domain.AccountRepository
import com.example.europrofile.domain.AuthRepository
import com.example.europrofile.domain.ConditionInfo
import com.example.europrofile.domain.OrderRepository
import com.example.europrofile.domain.ReviewRepository
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
    @Binds
    abstract fun provideReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository
    @Binds
    abstract fun provideCondRepository(conditionInfoImpl: ConditionInfoImpl) : ConditionInfo
    @Binds
    abstract fun provideOrderRepository(orderRepositoryImpl: OrderRepositoryImpl) : OrderRepository
}