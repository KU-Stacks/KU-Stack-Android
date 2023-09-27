package com.ku_stacks.ku_ring.user.repository

import com.ku_stacks.ku_ring.local.entity.BlackUserEntity
import com.ku_stacks.ku_ring.local.room.BlackUserDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: BlackUserDao
) : UserRepository {
    override fun blockUser(userId: String, nickname: String): Completable {
        return dao.blockUser(
            BlackUserEntity(
                userId = userId,
                nickname = nickname,
                blockedAt = System.currentTimeMillis()
            )
        )
    }

    override fun getBlackUserList(): Single<List<String>> {
        return dao.getBlackList()
            .map { blackList ->
                blackList.map { user -> user.userId }
            }
    }
}