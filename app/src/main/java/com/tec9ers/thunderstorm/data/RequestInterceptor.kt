package com.tec9ers.thunderstorm.data

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain) : Response {
        val request= chain.request()
        // TODO("ADD API KEY HERE")
        val myurl = request.url.newBuilder().addQueryParameter("appid", "ADD API KEY HERE").build()
        return chain.proceed(request.newBuilder().url(myurl).build())
    }
}