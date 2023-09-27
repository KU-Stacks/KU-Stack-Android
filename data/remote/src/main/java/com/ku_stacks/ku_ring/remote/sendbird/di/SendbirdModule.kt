package com.ku_stacks.ku_ring.remote.sendbird.di

import com.ku_stacks.ku_ring.remote.sendbird.SendbirdClient
import com.ku_stacks.ku_ring.remote.sendbird.SendbirdService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SendbirdModule {

    @Provides
    @Singleton
    fun provideSendbirdService(@Named("Sendbird") retrofit: Retrofit): SendbirdService {
        return retrofit.create(SendbirdService::class.java)
    }

    @Provides
    @Singleton
    fun provideSendbirdClient(sendbirdService: SendbirdService): SendbirdClient {
        return SendbirdClient(sendbirdService)
    }
}