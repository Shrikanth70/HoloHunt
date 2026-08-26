package com.vyra.app.core.media.di

import android.content.Context
import com.vyra.app.core.media.camera.CameraManager
import com.vyra.app.core.media.manager.LocalMediaManager
import com.vyra.app.core.media.validator.MediaValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Provides
    @Singleton
    fun provideMediaValidator(): MediaValidator = MediaValidator()

    @Provides
    @Singleton
    fun provideLocalMediaManager(
        @ApplicationContext context: Context,
    ): LocalMediaManager = LocalMediaManager(context)

    @Provides
    @Singleton
    fun provideCameraManager(
        @ApplicationContext context: Context,
    ): CameraManager = CameraManager(context)
}
