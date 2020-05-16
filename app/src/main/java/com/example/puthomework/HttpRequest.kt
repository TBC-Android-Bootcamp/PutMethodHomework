package com.example.puthomework

import android.util.Log
import android.util.Log.d
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*


object HttpRequest {
    const val UNKNOWN = "unknown/"
    const val CREATE = "create"
    const val USERS = "users"

    private const val HTTP_200_OK = 200
    private const val HTTP_201_CREATED = 201
    private const val HTTP_204_NO_CONTENT = 204
    private const val HTTP_400_BAD_REQUEST = 400
    private const val HTTP_401_UNAUTHORIZED = 401
    private const val HTTP_404_NOT_FOUND = 404
    private const val HTTP_500_INTERNAL_SERVER_ERROR = 500
    private const val HTTP_503_SERVICE_UNAVAILABLE = 503

    val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("https://reqres.in/api/")
        .build()

    val service = retrofit.create(ApiService::class.java)

    fun getRequest(path: String, id: String, callback: CustomCallback) {
        val call = service.getRequest(path, id)
        call.enqueue(onCallBack(callback))
    }

    fun postRequest(
        path: String,
        parameters: MutableMap<String, String>,
        callback: CustomCallback
    ) {
        val call = service.postRequest(path, parameters)
        call.enqueue(onCallBack(callback))
    }

    private fun onCallBack(callback: CustomCallback) = object : Callback<String> {
        override fun onFailure(call: Call<String>, t: Throwable) {
            d("onFailure", "${t.message}")
            callback.onFailed(t.message.toString())
        }

        override fun onResponse(call: Call<String>, response: Response<String>) {
            d("response", "${response.body()}")

            val statusCode = response.code()

            if (statusCode == HTTP_200_OK || statusCode == HTTP_201_CREATED) {
                callback.onSuccess(response.body().toString())
            } else if (statusCode == HTTP_204_NO_CONTENT) {
                callback.onFailed("No Content Received")
            } else if (statusCode == HTTP_400_BAD_REQUEST || statusCode == HTTP_401_UNAUTHORIZED || statusCode == HTTP_404_NOT_FOUND || statusCode == HTTP_500_INTERNAL_SERVER_ERROR || statusCode == HTTP_503_SERVICE_UNAVAILABLE) {
                try {
                    val json = JSONObject(response.errorBody()!!.string())
                    if (json.has("error")) {
                        callback.onFailed(json.getString("error"))
                    } else if (json.has("message")) {
                        callback.onFailed(json.getString("message"))
                    }
                } catch (e: JSONException) {
                    Log.d("exception", e.toString())
                }
            }
        }
    }



    interface ApiService {
        @GET("{path}")
        fun getRequest(@Path("path") path: String): Call<String>

        @GET("{path}/{id}")
        fun getRequest(
            @Path("path") path: String, @Path("id") id: String
        ): Call<String>

        @FormUrlEncoded
        @POST("{path}")
        fun postRequest(
            @Path("path") path: String,
            @FieldMap parameters: Map<String, String>
        ): Call<String>

        @FormUrlEncoded
        @PUT("{path}")
        fun updateUser(
            @Path("path") path: String,
            @FieldMap parameters: Map<String, String>
        ): Call<String>

    }

}