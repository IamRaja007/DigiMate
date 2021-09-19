package com.example.studentscompanion.response

import com.example.studentscompanion.Photo
import com.google.gson.annotations.SerializedName

data class GetAllQueriesResponse(

	@field:SerializedName("queries")
	val queries: List<QueriesItems?>? = null
)

data class ReplyItems(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("user")
	val user: String? = null
)

data class Pdfdocument(

//	@field:SerializedName("data")
//	val data: Data? = null,

	@field:SerializedName("contentType")
	val contentType: String? = null
)

//data class Data(
//
//	@field:SerializedName("data")
//	val data: List<Int?>? = null,
//
//	@field:SerializedName("type")
//	val type: String? = null
//)

data class Photo(

//	@field:SerializedName("data")
//	val data: Data? = null,

	@field:SerializedName("contentType")
	val contentType: String? = null
)

data class QueriesItems(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("pdfdocument")
	val pdfdocument: Pdfdocument? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("to")
	val to: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("reply")
	val reply: List<ReplyItems?>? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("photo")
	val photo: com.example.studentscompanion.response.Photo? = null
)
