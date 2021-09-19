package com.example.studentscompanion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentscompanion.R

import com.example.studentscompanion.response.ReplyItems
import com.example.studentscompanion.util.StudentClick
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_reply.view.*
import kotlinx.android.synthetic.main.item_row_under_proctor_list.view.*

class RepliesRecyclerAdapter(val context: Context, private val data: List<ReplyItems>) : RecyclerView.Adapter<RepliesRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepliesRecyclerAdapter.ViewHolder {
        val itemview = LayoutInflater.from(context).inflate(
            R.layout.item_row_reply,
            parent,
            false
        )

        println("Oncreate viewHolder Recycler View")

        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: RepliesRecyclerAdapter.ViewHolder, position: Int) {

        println("On bIND BINDING Recycler View")
        val myReplies = data[position]

        holder.name.text = myReplies.name
        holder.replyText.text = myReplies.text
//        holder.replyText.text =tex

        val link= "https://appbackend-100.herokuapp.com/api/users/view-profilepic/${myReplies.user}"
        Picasso.get()
            .load(link)
            .placeholder(R.drawable.default_avatar)
            .error(R.drawable.default_avatar)
            .into(holder.studentImage)


//        holder.studentName.text=myStudents.name
//        val text=myStudents.department + " (${myStudents.semester} Semester)"
//        holder.department.text=text

//        val link= "https://appbackend-100.herokuapp.com/api/users/view-profilepic/${myStudents.studentId}"
//        Picasso.get()
//            .load(link)
//            .placeholder(R.drawable.default_avatar)
//            .error(R.drawable.default_avatar)
//            .into(holder.profileImage)


    }


    override fun getItemCount(): Int {

        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var studentImage=itemView.civReplyProfile
        var name=itemView.tvReplierName
        var replyText=itemView.tvReplyText

//        init {
//            studentName.setOnClickListener {
//                studentClick.onStudentClick(data[adapterPosition].studentId!!)
//            }
//        }

    }

}
