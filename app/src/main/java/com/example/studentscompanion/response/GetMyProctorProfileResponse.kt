package com.example.studentscompanion.response

import com.google.gson.annotations.SerializedName

data class GetMyProctorProfileResponse(

    @field:SerializedName("isproctor")
    val isproctor: Boolean? = null,

    @field:SerializedName("institution")
    val institution: String? = null,

    @field:SerializedName("profilepic")
    val profilepic: Profilepic? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("designation")
    val designation: String? = null,

    @field:SerializedName("proctoring")
    val proctoring: List<ProctoringItem?>? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("queries")
    val queries: List<QueriesItem?>? = null,

    @field:SerializedName("email")
    val email: String? = null
)

data class Profilepic(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("contentType")
    val contentType: String? = null
)


data class Data(

    @field:SerializedName("data")
    val data: List<Int?>? = null,

    @field:SerializedName("type")
    val type: String? = null
)


