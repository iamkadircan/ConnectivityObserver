package com.example.connectivityobserver



import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{


    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context):NetworkMonitor{
        return ConnectivityMonitor(context)
    }

}