package com.example.studentscompanion.response

import com.google.gson.annotations.SerializedName

data class GetAllDocsResponse(

	@field:SerializedName("docs")
	val docs: List<DocsItem?>? = null
)

data class Photoe(

//	@field:SerializedName("data")
//	val data: Data? = null,

	@field:SerializedName("contentType")
	val contentType: String? = null
)

data class DocsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("institution")
	val institution: String? = null,

	@field:SerializedName("pdfdocument")
	val pdfdocument: Pdfdocument? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("photo")
	val photo: Photoe? = null,

	@field:SerializedName("semester")
	val semester: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("department")
	val department: String? = null,

	@field:SerializedName("reply")
	val reply: List<ReplyItems?>? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("time")
	val time: String? = null
)



