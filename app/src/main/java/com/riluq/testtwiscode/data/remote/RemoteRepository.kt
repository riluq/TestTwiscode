package com.riluq.testtwiscode.data.remote

import com.riluq.testtwiscode.data.remote.response.SearchResponse

interface RemoteRepository {
    suspend fun search(
        callback: ResponseCallback<List<SearchResponse>>
    )
}