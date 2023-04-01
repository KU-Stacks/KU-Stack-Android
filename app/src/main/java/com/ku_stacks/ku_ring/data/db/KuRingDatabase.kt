package com.ku_stacks.ku_ring.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        NoticeEntity::class,
        PushEntity::class,
        BlackUserEntity::class,
    ],
    version = 3,
    exportSchema = false
)
abstract class KuRingDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
    abstract fun pushDao(): PushDao
    abstract fun blackUserDao(): BlackUserDao
    abstract fun departmentDao(): DepartmentDao
}