package com.ku_stacks.ku_ring.data.api.response

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("code") val resultCode: Int,
    @SerializedName("message") val resultMsg: String,
    val data: Any?,
) {
    val isSuccess: Boolean
        get() = (resultCode == 200)
}