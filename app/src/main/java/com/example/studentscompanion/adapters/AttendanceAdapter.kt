package com.example.studentscompanion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.studentscompanion.R
import com.example.studentscompanion.response.ClassesItem
import com.example.studentscompanion.util.ClickInterface
import kotlinx.android.synthetic.main.item_row_class.view.*

class AttendanceAdapter(val context: Context, private val data: List<ClassesItem>,val userId:String,val rvclickinterface: ClickInterface) : RecyclerView.Adapter<AttendanceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceAdapter.ViewHolder {
        val itemview = LayoutInflater.from(context).inflate(
            R.layout.item_row_class,
            parent,
            false
        )

        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: AttendanceAdapter.ViewHolder, position: Int) {
        val myClasses = data[position]

        holder.date.text=myClasses.date
        holder.subject.text=myClasses.topic
        holder.teacher.text=myClasses.facultyName
        holder.time.text=myClasses.time


        if(myClasses.facultyId == userId){
            holder.btnGetAttendence.visibility=View.VISIBLE
            holder.btnJoinClass.visibility=View.GONE
        }
        else{
            holder.btnGetAttendence.visibility=View.GONE
            holder.btnJoinClass.visibility=View.VISIBLE
        }

    }


    override fun getItemCount(): Int {

        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var subject=itemView.tvTopic
        var teacher=itemView.tvFacultyName
        var time=itemView.tvTime
        var date=itemView.tvDate

        var btnGetAttendence: Button =itemView.btnGetAttendence
        var btnJoinClass:Button=itemView.btnJoinClass

        init {
            btnGetAttendence.setOnClickListener{
                data[adapterPosition].id?.let { it1 ->
                    rvclickinterface.onGetAttendanceClick(adapterPosition,
                        it1
                    )
                }
            }
        }

        init {
            btnJoinClass.setOnClickListener{
                data[adapterPosition].classLink?.let { it1 ->
                    rvclickinterface.onJoinClassClick(adapterPosition,
                        it1,
                        data[adapterPosition].id!!
                    )
                }
            }
        }

    }

}
