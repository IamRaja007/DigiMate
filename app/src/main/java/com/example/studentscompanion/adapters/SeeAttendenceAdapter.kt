package com.example.studentscompanion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentscompanion.R
import com.example.studentscompanion.response.AttendeesItem
import com.example.studentscompanion.response.ClassesItem
import com.example.studentscompanion.response.GetAttendenceResponse
import com.example.studentscompanion.util.ClickInterface
import kotlinx.android.synthetic.main.item_row_class.view.*
import kotlinx.android.synthetic.main.item_row_see_attendence.view.*

class SeeAttendanceAdapter(val context: Context, private val data: List<AttendeesItem>) : RecyclerView.Adapter<SeeAttendanceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAttendanceAdapter.ViewHolder {
        val itemview = LayoutInflater.from(context).inflate(
            R.layout.item_row_see_attendence,
            parent,
            false
        )

        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: SeeAttendanceAdapter.ViewHolder, position: Int) {
        val myAttendence = data[position]

//        holder.date.text=myClasses.date
//        holder.subject.text=myClasses.topic
//        holder.teacher.text=myClasses.facultyName
//        holder.time.text=myClasses.time
        holder.name.text=myAttendence.name

    }


    override fun getItemCount(): Int {

        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView =itemView.tvAttendeeName

    }

}
