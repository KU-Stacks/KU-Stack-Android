package com.ku_stacks.ku_ring.data.api.response

import com.google.gson.annotations.SerializedName

data class NoticeResponse(
    @SerializedName(value = "articleId")
    val articleId: String,
    @SerializedName(value = "postedDate")
    val postedDate: String,
    @SerializedName(value = "subject")
    val subject: String,
    @SerializedName(value = "category")
    val category: String
)
