package com.example.studentscompanion.response

import com.google.gson.annotations.SerializedName
data class StudentsUnderResponse(

	@field:SerializedName("user")
	val user: Userss? = null
)

data class Userss(

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
	val proctoring: List<ProctoringItemss?>? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)


data class ProctoringItemss(

	@field:SerializedName("studentId")
	val studentId: String? = null,

	@field:SerializedName("profilepic")
	val profilepic: Profilepic? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("semester")
	val semester: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("department")
	val department: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
