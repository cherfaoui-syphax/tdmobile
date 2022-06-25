package com.example.tdmobile.retrofit

import com.example.tdmobile.entity.ReservationResponse
import com.example.tdmobile.entity.loginResponse
import com.example.tdmobile.entity.parkingsResponse
import com.example.tdmobile.entity.signUpResponse
import com.example.tdmobile.url
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File
import java.util.concurrent.TimeUnit

interface RetrofitService  {
    @FormUrlEncoded

    @POST("/signup")
    suspend fun signUp(@Field(value = "firstName") firstName:String,
                       @Field(value = "lastName") lastName:String,
                       @Field(value = "password") password:String,
                       @Field(value = "email") email:String,
                       @Field(value = "phone") phone:Int
    ):Response<signUpResponse>
    @FormUrlEncoded
    @POST("/login")
    suspend fun signIn(@FieldMap data:Map<String,String>):Response<loginResponse>
    @GET("/parkings")
    fun getAllParkings(): Call<parkingsResponse>
    @FormUrlEncoded
    @POST("/parkings")
    suspend fun getAvailibreParkings(@FieldMap data:Map<String,String>):Call<parkingsResponse>
    @GET("/reservations/byUser/{id}")
    fun getMyReservations(@Path("id") userId:String ):Call<ReservationResponse>
    @FormUrlEncoded
    @POST("/reservations")
    suspend fun getMyActiveReservations(@FieldMap data:Map<String,String>):Response<loginResponse>

   // @GET("movies/parkings")
   //  suspend fun getAllParkings(): Response<List<Parking>>

   // @GET("movies/getbytitle/{title}")
   // suspend fun getMoviesByTitle(@Path("title") title: String): Response<List<Movie>>

    companion object {

        @Volatile
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {

                val cacheSize = 10 * 1024 * 1024 // 10 MB
                val httpCacheDirectory = File("http-cache")
                println(httpCacheDirectory.getAbsolutePath());
                val cache = Cache(httpCacheDirectory, cacheSize.toLong())

                // create a network cache interceptor, setting the max age to 1 minute
                val networkCacheInterceptor = Interceptor { chain ->
                    val response = chain.proceed(chain.request())

                    var cacheControl = CacheControl.Builder()
                        .maxAge(1, TimeUnit.MINUTES)
                        .build()

                    response.newBuilder()
                        .header("Cache-Control", cacheControl.toString())
                        .build()
                }

               val offlineInterceptor =  Interceptor { chain ->
                    var request: Request = chain.request()
                    val originalResponse: okhttp3.Response = chain.proceed(request)
                    val cacheControl: String? = originalResponse.header("Cache-Control")
                    if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
                    ) {
                        val cc = CacheControl.Builder()
                            .maxStale(1, TimeUnit.DAYS)
                            .build()
                        request = request.newBuilder()
                            .removeHeader("Pragma")
                            .cacheControl(cc)
                            .build()
                        chain.proceed(request)
                    } else {
                        originalResponse

                    }
                }


                // Create the logging interceptor
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


                val httpClient = OkHttpClient.Builder()
                    .cache(cache)
                    .addNetworkInterceptor(networkCacheInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(offlineInterceptor)
                    .build()



                val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }

}
