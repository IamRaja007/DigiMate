package com.example.studentscompanion.response

import com.google.gson.annotations.SerializedName

data class CreateQueryResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("photo")
	val photo: Photos? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("to")
	val to: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("reply")
	val reply: List<Any?>? = null,

	@field:SerializedName("user")
	val user: String? = null
)

data class Photos(

//	@field:SerializedName("data")
//	val data: Data? = null,

	@field:SerializedName("contentType")
	val contentType: String? = null
)

