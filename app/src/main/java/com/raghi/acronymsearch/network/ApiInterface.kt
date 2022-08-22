package com.raghi.acronymsearch.network

import com.raghi.acronymsearch.model.AcronymResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("dictionary.py")
    suspend fun acroList(@Query("sf") sf: String): Response<List<AcronymResponse>>
}
