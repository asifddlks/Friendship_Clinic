package dev.asifddlks.friendshipclinic.network

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.JsonElement
import dev.asifddlks.bhaibhaiclinicApp.utils.constants.ApiConstants
import dev.asifddlks.friendshipclinic.FriendshipClinicApplication
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File

class NetworkManager {

    companion object {

        const val TEMP_AUTHORIZATION: String =
            "5f54c461fb7d6b345b3a770fa4586795ec1125bb80459ed9daba5f1c26cf26e0"

        private val oktHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(FriendshipClinicApplication.applicationContext()))

        val retrofitClient: Api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TEMP_AUTHORIZATION").build()
                chain.proceed(request)
            }.build())
            //.client(oktHttpClient.build())
            .build().create(Api::class.java)
    }

    fun getRequest(
        url: String,
        listener: RequestListener
    ) {
        val accessToken = TEMP_AUTHORIZATION

        if (accessToken.isEmpty()) {
            retrofitClient.getData(url).enqueue(APICallBack(listener = listener))
        } else {
            retrofitClient.getData(accessToken, url).enqueue(APICallBack(listener = listener))
        }

    }

    fun postRequest(
        url: String,
        params: Map<String, @JvmSuppressWildcards Any?>,
        listener: RequestListener
    ) {
        val accessToken = TEMP_AUTHORIZATION

        if (accessToken.isEmpty()) {
            retrofitClient.postData(url, params)
                .enqueue(APICallBack(params = params, listener = listener))
        } else {
            retrofitClient.postData(accessToken, url, params)
                .enqueue(APICallBack(params = params, listener = listener))
        }
    }


    fun deleteRequest(
        url: String,
        params: Map<String, @JvmSuppressWildcards Any?>,
        listener: RequestListener
    ) {
        val accessToken = TEMP_AUTHORIZATION

        if (accessToken.isEmpty()) {
            retrofitClient.deleteData(url, params)
                .enqueue(APICallBack(params = params, listener = listener))
        } else {
            retrofitClient.deleteData(accessToken, url, params)
                .enqueue(APICallBack(params = params, listener = listener))
        }
    }

    fun putRequest(
        url: String,
        params: Map<String, @JvmSuppressWildcards Any?>,
        listener: RequestListener
    ) {
        val accessToken = TEMP_AUTHORIZATION

        if (accessToken.isEmpty()) {
            retrofitClient.putData(url, params)
                .enqueue(APICallBack(params = params, listener = listener))
        } else {
            retrofitClient.putData(accessToken, url, params)
                .enqueue(APICallBack(params = params, listener = listener))
        }
    }

    fun fileUpload(
        url: String,
        file: File,
        accessToken: String?,
        listener: RequestListener
    ) {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val fileType = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        retrofitClient.fileUpload(TEMP_AUTHORIZATION, url, body, fileType)
            .enqueue(APICallBack(listener = listener))
    }
}

interface Api {
    @POST
    @Headers("Content-Type: application/json")
    fun postData(
        @Url url: String,
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Call<JsonElement?>

    @POST
    @Headers("Content-Type: application/json")
    fun postData(
        @Header(NetworkManager.TEMP_AUTHORIZATION) authorization: String,
        @Url url: String,
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Call<JsonElement?>

    @GET
    @Headers("Content-Type: application/json")
    fun getData(
        @Header(NetworkManager.TEMP_AUTHORIZATION) authorization: String,
        @Url url: String?
    ): Call<JsonElement?>

    @GET
    fun getData(
        @Url url: String?
    ): Call<JsonElement?>

    @Multipart
    @POST
    fun fileUpload(
        @Header(NetworkManager.TEMP_AUTHORIZATION) authorization: String,
        @Url url: String?,
        @Part file: MultipartBody.Part?,
        @Part("file_type") file_type: RequestBody?
    ): Call<JsonElement?>

    @HTTP(method = "DELETE", hasBody = true)
    @Headers("Content-Type: application/json")
    fun deleteData(
        @Url url: String,
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Call<JsonElement?>

    @HTTP(method = "DELETE", hasBody = true)
    @Headers("Content-Type: application/json")
    fun deleteData(
        @Header(NetworkManager.TEMP_AUTHORIZATION) authorization: String,
        @Url url: String,
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Call<JsonElement?>

    @HTTP(method = "PUT", hasBody = true)
    @Headers("Content-Type: application/json")
    fun putData(
        @Url url: String,
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Call<JsonElement?>

    @HTTP(method = "PUT", hasBody = true)
    @Headers("Content-Type: application/json")
    fun putData(
        @Header(NetworkManager.TEMP_AUTHORIZATION) authorization: String,
        @Url url: String,
        @Body params: Map<String, @JvmSuppressWildcards Any?>
    ): Call<JsonElement?>
}

class APICallBack<T>(
    private val params: Map<String, @JvmSuppressWildcards Any?>? = null,
    private val listener: RequestListener
) : Callback<T> {
    private val tag: String = this.javaClass.simpleName

    @SuppressLint("LogNotTimber")
    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            Log.d(
                tag,
                "accessToken: ${NetworkManager.TEMP_AUTHORIZATION}"
            )

            Log.d(
                tag,
                "url: (method: ${call.request().method()}): ${call.request().url()}"
            )
            Log.d(tag, "headers(): ${call.request().headers()}")
            params?.let { Log.d(tag, "params: $params") }
            Log.d(tag, "onResponse: $response")

            if (response.code() == 200) {
                Log.d(tag, "onSuccess: ${response.body()}")
                listener.onSuccess(response)
            } else {
                response.errorBody()?.let {

                    var message = ""
                    val errorBody = it.string()
                    Log.e(tag, "onFailure: errorBody: $errorBody")

                    try {
                        val body = JSONObject(errorBody)
                        message = body.getString("message")
                        val code = body.getInt("code")


                        when (ErrorCodeEnum.valueOf(code)) {
                            ErrorCodeEnum.OK -> {}
                            ErrorCodeEnum.Canceled -> {}
                            ErrorCodeEnum.Unknown -> {}
                            ErrorCodeEnum.InvalidArgument -> {}
                            ErrorCodeEnum.DeadlineExceeded -> {}
                            ErrorCodeEnum.NotFound -> {}
                            ErrorCodeEnum.AlreadyExists -> {}
                            ErrorCodeEnum.PermissionDenied -> {}
                            ErrorCodeEnum.ResourceExhausted -> {}
                            ErrorCodeEnum.FailedPrecondition -> {}
                            ErrorCodeEnum.Aborted -> {}
                            ErrorCodeEnum.OutOfRange -> {}
                            ErrorCodeEnum.Unimplemented -> {}
                            ErrorCodeEnum.Internal -> {}
                            ErrorCodeEnum.Unavailable -> {}
                            ErrorCodeEnum.DataLoss -> {}
                            ErrorCodeEnum.Unauthenticated -> {}
                            null -> {}
                        }
                    } catch (e: Exception) {
                        message = errorBody
                    }
                    listener.onError(message)
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "onFailure: (catch): $e")
            listener.onError(e.toString())
        }
    }

    @SuppressLint("LogNotTimber")
    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is NoConnectivityException) {
            // show No Connectivity message to user or do whatever you want.
            Log.e(tag, "onFailure: NoConnectivityException: ${t.message}")
        }
        Log.e(
            tag,
            "url: (method: ${call.request().method()}): ${call.request().url()}"
        )
        Log.e(tag, "onFailure: ${t.message}")
        listener.onError(t.message.toString())
    }
}

@JvmSuppressWildcards
interface RequestListener {
    fun onSuccess(response: @JvmSuppressWildcards Any?)
    fun onError(error: String)
}