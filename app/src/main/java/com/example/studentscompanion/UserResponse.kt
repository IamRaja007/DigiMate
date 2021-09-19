package com.example.studentscompanion

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class User(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("followers")
	val followers: List<Any?>? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("profilepic")
	val profilepic: Profilepic? = null,

	@field:SerializedName("following")
	val following: List<Any?>? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("about")
	val about: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null
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


