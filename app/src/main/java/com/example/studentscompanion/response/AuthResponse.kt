package com.example.studentscompanion.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class AuthResponse(


    @field:SerializedName("user")
    val user: @RawValue User? = null,

    @field:SerializedName("token")
    val token: String? = null
) : Parcelable

@Parcelize
data class ReplyItem(

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("text")
    val text: String? = null
) : Parcelable

@Parcelize
data class QueriesItem(

    @field:SerializedName("from")
    val from: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("reply")
    val reply: List<ReplyItem?>? = null
) : Parcelable

@Parcelize
data class ProctoringItem(

    @field:SerializedName("studentId")
    val studentId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null
) : Parcelable

@Parcelize
data class User(

    @field:SerializedName("isproctor")
    val isproctor: Boolean? = null,

    @field:SerializedName("institution")
    val institution: String? = null,

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
) : Parcelable