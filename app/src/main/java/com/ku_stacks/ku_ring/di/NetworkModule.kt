package com.ku_stacks.ku_ring.di

import com.ku_stacks.ku_ring.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNoticeService(@Named("Default") retrofit: Retrofit): NoticeService {
        return retrofit.create(NoticeService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchService(@Named("Default") retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideNoticeClient(noticeService: NoticeService): NoticeClient {
        return NoticeClient(noticeService)
    }

    @Provides
    @Singleton
    fun provideFeedbackService(@Named("Default") retrofit: Retrofit): FeedbackService {
        return retrofit.create(FeedbackService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedbackClient(feedbackService: FeedbackService): FeedbackClient {
        return FeedbackClient(feedbackService)
    }

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

    @Provides
    @Singleton
    fun provideDepartmentService(@Named("Default") retrofit: Retrofit): DepartmentService {
        return retrofit.create(DepartmentService::class.java)
    }
}