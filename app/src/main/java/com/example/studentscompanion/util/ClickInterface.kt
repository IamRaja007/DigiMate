package com.example.studentscompanion.util

interface ClickInterface {
    fun onJoinClassClick(position:Int,classLink:String,classId:String):Unit
    fun onGetAttendanceClick(position: Int,classId: String):Unit
}