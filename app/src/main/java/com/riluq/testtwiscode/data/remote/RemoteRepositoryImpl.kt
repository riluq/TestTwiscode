package com.riluq.testtwiscode.data.remote

import com.riluq.testtwiscode.data.remote.response.SearchResponse
import com.riluq.testtwiscode.vo.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val responseHandler: ResponseHandler
): RemoteRepository {

    override suspend fun search(callback: ResponseCallback<List<SearchResponse>>) {
        withContext(Dispatchers.IO) {
            val response = api.search()
            try {
                if (response.isSuccessful) {
                    callback.onComplete(responseHandler.handleSuccess(response.body()!!))
                } else {
                    callback.onErrorResponse(responseHandler.handleError(response.errorBody()!!))
                }
            } catch (exception: Exception) {
                callback.onFailed(responseHandler.handleException(exception), exception)
            }
        }
    }
}