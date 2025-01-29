/*
 * Copyright (c) 2025, Glassdoor Inc.
 *
 * Licensed under the Glassdoor Inc Hiring Assessment License.
 * You may not use this file except in compliance with the License.
 * You must obtain explicit permission from Glassdoor Inc before sharing or distributing this file.
 * Mention Glassdoor Inc as the source if you use this code in any way.
 */

package com.glassdoor.intern.data.network

import com.glassdoor.intern.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber
import javax.inject.Inject

private const val TOKEN_KEY: String = "token"

/**
 * DONE: Declare the email address from your resume as a token
 */
private const val TOKEN_VALUE: String = "pkapadn@clemson.edu"

internal class TokenInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalUrl = request.url

        // ✅ Step 1: Add token to the request if it matches the API endpoint
        if (originalUrl.toString().endsWith(BuildConfig.ENDPOINT_GET_INFO)) {
            val urlWithToken = originalUrl.newBuilder()
                .addQueryParameter("token", "pkapadn@clemson.edu") // Replace with actual token
                .build()

            request = request.newBuilder().url(urlWithToken).build()
        }

        var response = chain.proceed(request)

        // ✅ Step 2: Check for 302 Redirect
        val redirectUrl = response.header("Location") // Get the redirected URL
        if (response.code == 302 && !redirectUrl.isNullOrEmpty()) {
            Timber.w("TokenInterceptor: Redirecting to -> $redirectUrl")

            response.close() // ✅ Close the previous response

            // ✅ Step 3: Fetch the actual data from the redirected URL
            val redirectedRequest = request.newBuilder().url(redirectUrl).build()
            response = chain.proceed(redirectedRequest) // Follow the redirect

            if (response.isSuccessful) {
                val responseBodyString = response.body?.string() ?: "" // ✅ Get the JSON body

                Timber.d("TokenInterceptor: Fetched Data -> $responseBodyString") // ✅ Log the actual JSON data

                // ✅ Step 4: Create a NEW response with the JSON body
                return response.newBuilder()
                    .body(responseBodyString.toResponseBody(response.body?.contentType()))
                    .build()
            }
        }

        return response.newBuilder()
            .body(response.body?.string()?.toResponseBody(response.body?.contentType()))
            .build() // ✅ Always return the final response containing JSON data
    }
}

