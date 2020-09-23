package com.tec9ers.thunderstorm.data

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain) : Response {
        val request= chain.request()
        // TODO("ADD API KEY HERE")
        val myurl = request.url.newBuilder().addQueryParameter("appid", "5239cb4b43a7769237255c39ba8c6bbd").build()
        return chain.proceed(request.newBuilder().url(myurl).build())
    }
}