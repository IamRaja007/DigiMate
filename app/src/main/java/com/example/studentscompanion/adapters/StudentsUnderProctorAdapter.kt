package com.example.studentscompanion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentscompanion.R
import com.example.studentscompanion.response.ProctoringItemss
import com.example.studentscompanion.util.StudentClick
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_under_proctor_list.view.*

class StudentsUnderProctorAdapter(val context: Context, private val data: List<ProctoringItemss>) : RecyclerView.Adapter<StudentsUnderProctorAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsUnderProctorAdapter.ViewHolder {
        val itemview = LayoutInflater.from(context).inflate(
            R.layout.item_row_under_proctor_list,
            parent,
            false
        )

        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: StudentsUnderProctorAdapter.ViewHolder, position: Int) {
        val myStudents = data[position]

        holder.studentName.text=myStudents.name
        val text=myStudents.department + " (${myStudents.semester} Semester)"
        holder.department.text=text

        val link= "https://appbackend-100.herokuapp.com/api/users/view-profilepic/${myStudents.studentId}"
        Picasso.get()
            .load(link)
            .placeholder(R.drawable.default_avatar)
            .error(R.drawable.default_avatar)
            .into(holder.profileImage)


    }


    override fun getItemCount(): Int {

        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var studentName=itemView.tvUnderStudentName
        var department=itemView.tvUnderStudentDepartmentName
        var profileImage=itemView.civUnderStudent

//        init {
//            studentName.setOnClickListener {
//                studentClick.onStudentClick(data[adapterPosition].studentId!!)
//            }
//        }

    }

}
