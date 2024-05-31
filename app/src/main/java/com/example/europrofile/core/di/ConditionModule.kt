package com.example.europrofile.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface AssistedInjectModule
/*
object ConditionModule {

    @Provides
    fun providesConditionerViewModelFactory(
        rep: ConditionInfo,
        user: User
    ): ConditionerViewModelFactory = ConditionerViewModelFactory(rep, user)

}*/
