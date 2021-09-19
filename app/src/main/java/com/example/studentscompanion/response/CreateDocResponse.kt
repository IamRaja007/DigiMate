package com.example.studentscompanion.response

import com.google.gson.annotations.SerializedName


data class CreateDocResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("pdfdocument")
	val pdfdocument: Pdfdocument? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("reply")
	val reply: List<Any?>? = null,

	@field:SerializedName("user")
	val user: String? = null
)



