package com.riluq.testtwiscode.data.remote

import com.riluq.testtwiscode.vo.Resource

interface ResponseCallback<Response> {
    fun onComplete(response: Resource<Response>)
    fun onErrorResponse(response: Resource<Response>)
    fun onFailed(response: Resource<Response>, exception: Exception)
}