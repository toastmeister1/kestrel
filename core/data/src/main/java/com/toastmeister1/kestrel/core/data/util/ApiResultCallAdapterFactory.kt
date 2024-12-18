package com.toastmeister1.kestrel.core.data.util

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ApiResultCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Call<ApiResult<R>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<ApiResult<R>> = ApiResultCall(call, responseType)
}

internal class ApiResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            Log.e(this.javaClass.simpleName, "Call<Type>의 형태가 아닙니다")
            return null
        }
        // Return Type이 제네릭 인자를 가지는지 체크
        check(returnType is ParameterizedType) { "리턴 타입은 반드시 Call<ApiResult<<Foo>> 와 같은 parameterized Type 이여야 합니다" }

        val wrapperType = getParameterUpperBound(0, returnType)
        if (getRawType(wrapperType) != ApiResult::class.java) {
            Log.e(this.javaClass.simpleName, "ApiResult<Type>의 형태가 아닙니다")
            return null
        }
        check(wrapperType is ParameterizedType) { "리턴 타입은 반드시 ApiResult<Foo> 와 같은 parameterized Type 이여야 합니다" }

        val bodyType = getParameterUpperBound(0, wrapperType)
        return ApiResultCallAdapter<Any>(bodyType)
    }
}

private class ApiResultCall<R>(
    private val delegate: Call<R>,
    private val responseType: Type
) : Call<ApiResult<R>> {

    override fun enqueue(callback: Callback<ApiResult<R>>) = delegate.enqueue(
        object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@ApiResultCall, Response.success(parseResponse(response)))
            }

            override fun onFailure(call: Call<R?>, throwable: Throwable) {
                val error = ApiResult.Exception(throwable)
                callback.onResponse(this@ApiResultCall, Response.success(error))
            }

            private fun parseResponse(response: Response<R>): ApiResult<R> {
                val body = response.body()
                val code = response.code()
                val errorBody = response.errorBody()

                return if (response.isSuccessful) {
                    if (body != null) {
                        ApiResult.Success(body)
                    } else {
                        ApiResult.Error(
                            code = code,
                            message = "Body가 Null입니다.",
                            body = errorBody?.string() ?: ""
                        )
                    }
                } else {
                    ApiResult.Error(
                        code = code,
                        message = response.message(),
                        body = errorBody?.string() ?: ""
                    )
                }
            }
        }
    )

    override fun clone(): Call<ApiResult<R>> = ApiResultCall(delegate.clone(), responseType)
    override fun execute(): Response<ApiResult<R>> = throw NotImplementedError()
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun timeout(): Timeout = delegate.timeout()
    override fun request(): Request = delegate.request()
    override fun cancel() = delegate.cancel()
}