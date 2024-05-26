package com.toastmeister1.kestrel.core.data.interceptor

import com.toastmeister1.kestrel.core.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            url(
                original.url.newBuilder().apply {
                    addHeader(name = "Authorization", value = BuildConfig.TMDB_API_KEY)
                }.build(),
            )
        }.method(original.method, original.body).build()
        return chain.proceed(request)
    }
}
