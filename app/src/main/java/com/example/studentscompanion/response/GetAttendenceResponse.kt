package com.example.studentscompanion.response

import com.google.gson.annotations.SerializedName

data class GetAttendenceResponse(

	@field:SerializedName("attendees")
	val attendees: List<AttendeesItem?>? = null
)

data class AttendeesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
