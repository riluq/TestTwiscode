package com.riluq.testtwiscode.data.remote

import com.riluq.testtwiscode.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("index.php/rest/items/search/api_key/teampsisthebest")
    suspend fun search(): Response<List<SearchResponse>>
}