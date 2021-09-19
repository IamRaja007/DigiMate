package com.example.studentscompanion.response

import com.google.gson.annotations.SerializedName

data class CreateClassResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("facultyId")
	val facultyId: String? = null,

	@field:SerializedName("classID")
	val classID: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("topic")
	val topic: String? = null,

	@field:SerializedName("classLink")
	val classLink: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("facultyName")
	val facultyName: String? = null
)
