package com.ku_stacks.ku_ring.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoticeEntity(
    @PrimaryKey @ColumnInfo(name = "articleId") val articleId: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "postedDate") val postedDate: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "isNew") val isNew: Boolean,
    @ColumnInfo(name = "isRead") val isRead: Boolean,
    @ColumnInfo(name = "isSaved") val isSaved: Boolean,
    @ColumnInfo(name = "isReadOnStorage") val isReadOnStorage: Boolean,
)