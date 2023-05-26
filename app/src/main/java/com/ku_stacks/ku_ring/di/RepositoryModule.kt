package com.ku_stacks.ku_ring.di

import com.ku_stacks.ku_ring.data.api.DepartmentClient
import com.ku_stacks.ku_ring.data.api.NoticeClient
import com.ku_stacks.ku_ring.data.api.SendbirdClient
import com.ku_stacks.ku_ring.data.db.BlackUserDao
import com.ku_stacks.ku_ring.data.db.DepartmentDao
import com.ku_stacks.ku_ring.data.db.NoticeDao
import com.ku_stacks.ku_ring.data.db.PushDao
import com.ku_stacks.ku_ring.repository.*
import com.ku_stacks.ku_ring.util.PreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoticeRepository(
        noticeClient: NoticeClient,
        noticeDao: NoticeDao,
        pref: PreferenceUtil,
    ): NoticeRepository {
        return NoticeRepositoryImpl(noticeClient, noticeDao, pref, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun providePushRepository(
        pushDao: PushDao
    ): PushRepository {
        return PushRepositoryImpl(pushDao)
    }

    @Provides
    @Singleton
    fun provideSubscribeRepository(
        noticeClient: NoticeClient,
        departmentClient: DepartmentClient,
        pref: PreferenceUtil
    ): SubscribeRepository {
        return SubscribeRepositoryImpl(noticeClient, departmentClient, pref)
    }

    @Provides
    @Singleton
    fun provideSendbirdRepository(
        sendbirdClient: SendbirdClient
    ): SendbirdRepository {
        return SendbirdRepositoryImpl(sendbirdClient)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        blackUserDao: BlackUserDao
    ): UserRepository {
        return UserRepositoryImpl(blackUserDao)
    }

    @Provides
    @Singleton
    fun provideDepartmentNoticeRepository(
        noticeClient: NoticeClient,
        noticeDao: NoticeDao,
        pref: PreferenceUtil,
    ): DepartmentNoticeRepository = DepartmentNoticeRepositoryImpl(noticeClient, noticeDao, pref)

    @Provides
    @Singleton
    fun provideDepartmentRepository(
        departmentDao: DepartmentDao,
        departmentClient: DepartmentClient
    ): DepartmentRepository = DepartmentRepositoryImpl(departmentDao, departmentClient)

}