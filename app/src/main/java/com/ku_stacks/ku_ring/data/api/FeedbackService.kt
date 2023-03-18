package com.ku_stacks.ku_ring.data.api

import com.ku_stacks.ku_ring.data.api.request.FeedbackRequest
import com.ku_stacks.ku_ring.data.api.response.DefaultResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackService {
    @POST("v1/feedback")
    fun sendFeedback(
        @Body feedbackRequest: FeedbackRequest
    ): Single<DefaultResponse>
}