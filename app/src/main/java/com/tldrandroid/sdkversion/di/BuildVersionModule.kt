package com.tldrandroid.sdkversion.di

import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class BuildVersionModule {

    companion object {
        const val SDK_INT = "com.tldrandroid.sdkversion.di.BuildVersionModule.SDK_INT"
    }

    @Provides
    @Named(SDK_INT)
    fun buildSdkInt() = Build.VERSION.SDK_INT
}
