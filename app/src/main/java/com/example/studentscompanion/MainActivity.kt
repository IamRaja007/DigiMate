package com.example.studentscompanion

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG="MAIN_ACTIVITY"
class MainActivity : AppCompatActivity() {

    var token: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["email"] = "bishalmukherjee448@gmail.com"
        requestBody["password"] = "1234567"

//        val retrofit=RetrofitBuilder.apiService.getAuth(requestBody)
//        retrofit.enqueue(object : Callback<UserResponse> {
//            override fun onResponse(call: Call<UserResponse>, response: StudentProfileResponse<UserResponse>) {
//                Log.d(TAG,response.body().toString())
//                val obj=response.body()
//                token=obj?.token
//                Log.d(
//                    TAG,
//                    "TOKEN = $token"
//                )
//
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                Log.d(TAG,"Failed")
//            }
//
//        })
//
//        btnGet.setOnClickListener {
//            if(token!=null){
//                val posts=RetrofitBuilder.apiService.getPosts(token!!)
//                posts.enqueue(object : Callback<PostResponse>{
//                    override fun onResponse(
//                        call: Call<PostResponse>,
//                        response: StudentProfileResponse<PostResponse>
//                    ) {
//                        Log.d(TAG,response.code().toString())
//                        Log.d(TAG,response.body().toString())
//                        val obj=response.body()
//                        for( i in obj?.posts!!){
//                            Log.d(TAG,"id = ${i?._id}")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
//                        Log.d(TAG,"posts failed fetch $t")
//                    }
//
//                })
//            }
//        }


    }
}