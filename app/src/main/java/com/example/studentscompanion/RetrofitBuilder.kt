package com.example.studentscompanion

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {    //Here we are following Singleton pattern

//    private val okhttp = OkHttpClient.Builder().addInterceptor(MyInterceptor())

    val retrofitBuilder: Retrofit.Builder by lazy {  //the benefit of using lazy is that the object will be created only when it is called otherwise it will not be created.
        // The other benefit of using lazy is that once the object is initialized, you will use the same object again when called.
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  //Gson converter factory converts json pbjects to java objects
//            .client(okhttp.build())
    }

    val apiService: ApiService by lazy {
        retrofitBuilder.build()
            .create(ApiService::class.java)
    }
}