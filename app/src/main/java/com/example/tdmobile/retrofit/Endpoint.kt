package com.example.tdmobile.retrofit

import com.example.tdmobile.entity.loginResponse
import com.example.tdmobile.entity.parkingsResponse
import com.example.tdmobile.entity.signUpResponse
import com.example.tdmobile.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Endpoint {
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
    suspend fun getAllParkings():Response<parkingsResponse>
    @FormUrlEncoded
    @POST("/parkings")
    suspend fun getAvailibreParkings(@FieldMap data:Map<String,String>):Response<parkingsResponse>
    @FormUrlEncoded
    @POST("/reservations")
    suspend fun getMyReservations(@FieldMap data:Map<String,String>):Response<loginResponse>
    @FormUrlEncoded
    @POST("/reservations")
    suspend fun getMyActiveReservations(@FieldMap data:Map<String,String>):Response<loginResponse>

   // @GET("movies/parkings")
   //  suspend fun getAllParkings(): Response<List<Parking>>

   // @GET("movies/getbytitle/{title}")
   // suspend fun getMoviesByTitle(@Path("title") title: String): Response<List<Movie>>

    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createInstance(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }

}
