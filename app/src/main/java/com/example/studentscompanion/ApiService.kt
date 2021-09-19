package com.example.studentscompanion

import com.example.studentscompanion.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

//    @GET("posts/all_posts")
//    fun getPosts(@Header("x-auth-token") authToken:String): Call<PostResponse>

    @GET("classes/todays-classes")
    fun getAllClasses(): Call<GetClassesResponse>

    @POST("auth")
    fun getAuth(@Body body:Map<String,String>): Call<AuthResponse>

    @GET("classes/{id}/attendence-list")
    fun getAttendence(@Path("id") classId:String,@Header("auth-token")authToken:String):Call<GetAttendenceResponse>

    @POST("classes")
    fun createClass(@Body bodyClass:Map<String,String>,@Header("auth-token")authToken:String):Call<CreateClassResponse>

    @GET("classes/attend/{id}")
    fun attendClass(@Path("id") classId:String, @Header("auth-token") authToken:String):Call<Unit>

//    @GET("users/get-myproctor-profile")
//    fun getMyProctorProfile(@Header("auth-token") authToken:String):Call<GetMyProctorProfileResponse>

    @GET("users/my-profile")
    fun getProctorProfile(@Header("auth-token") authToken:String):Call<StudentsUnderResponse>

    @GET("users/my-profile")
    fun getStudentProfile(@Header("auth-token")authToken:String):Call<StudentProfileResponse>

    @GET("queries/get-all-queries")
    fun getAllQueries(@Header("auth-token") authToken:String):Call<GetAllQueriesResponse>

    @PUT("queries/{id}/write-reply")
    fun writeReply(@Header("auth-token") authToken: String ,@Path("id") id:String,@Body bodyWriteReply:Map<String,String>):Call<WriteReplyResponse>

    @PUT("docs/{id}/write-reply")
    fun writeReplyInDoc(@Header("auth-token") authToken: String ,@Path("id") id:String,@Body bodyWriteReply:Map<String,String>):Call<WriteReplyResponse>

    @Multipart
    @POST("queries")
    fun createQueryWithTextPhoto(@Header("auth-token") authToken: String,@Part image:MultipartBody.Part?=null,@Part("text") queryText:RequestBody?=null ,@Part("time") queryTime:RequestBody): Call<CreateQueryResponse>

    @Multipart
    @POST("queries")
    fun createQueryWithTextPdf(@Header("auth-token") authToken: String,@Part filePdf:MultipartBody.Part?=null,@Part("text") queryText:RequestBody?=null,@Part("time") queryTime:RequestBody ): Call<CreateQueryWithPdfResponse>

    @Multipart
    @POST("docs")
    fun createDocWithTextPhoto(@Header("auth-token") authToken: String,@Part image:MultipartBody.Part?=null,@Part("text") queryText:RequestBody?=null,@Part("time") queryTime:RequestBody ): Call<CreateDocResponse>

    @Multipart
    @POST("docs")
    fun createDocWithTextPdf(@Header("auth-token") authToken: String,@Part filePdf:MultipartBody.Part?=null,@Part("text") queryText:RequestBody?=null,@Part("time") queryTime:RequestBody ): Call<CreateDocWithPdfResponse>


    @GET("docs/get-all-docs")
    fun getAllDocs(@Header("auth-token") authToken: String):Call<GetAllDocsResponse>
}