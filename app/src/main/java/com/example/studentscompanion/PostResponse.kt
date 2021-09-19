package com.example.studentscompanion

import com.google.gson.annotations.SerializedName

data class PostResponse(
	@SerializedName("posts")
	val posts: List<PostsItem?>? = null
)

data class LikesItem(
	val profilepic: Int? = null,
	val name: String? = null,
	val _id: String? = null,
	val user: String? = null
)

data class CommentsItem(
	val date: String? = null,
	val profilepic: Int? = null,
	val name: String? = null,
	val _id: String? = null,
	val text: String? = null,
	val user: String? = null
)

data class PostsItem(
	val date: String? = null,
	val comments: List<CommentsItem?>? = null,
	val profilepic: Int? = null,
	val _v: Int? = null,
	val name: String? = null,
	val _id: String? = null,
	val text: String? = null,
	val user: String? = null,
	val likes: List<LikesItem?>? = null,
	val photo: Photo? = null
)

data class Photo(
	val data: Data? = null,
	val contentType: String? = null
)