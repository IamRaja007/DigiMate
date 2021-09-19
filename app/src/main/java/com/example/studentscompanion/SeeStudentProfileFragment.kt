package com.example.studentscompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.example.studentscompanion.response.StudentProfileResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_proctoring.*
import kotlinx.android.synthetic.main.fragment_see_student_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class SeeStudentProfileFragment : Fragment() {
//
//    val args: SeeAttendenceFragmentArgs by navArgs()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_see_student_profile, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        val pref=context?.getSharedPreferences("Mytoken",0)
////        auth=pref?.getString("auth","")
//
//        println("Student profile id =${args.id}")
//
//        val request=RetrofitBuilder.apiService.getStudentProfile(args.id)
//        request.enqueue(object : Callback<StudentProfileResponse>{
//            override fun onResponse(
//                call: Call<StudentProfileResponse>,
//                response: Response<StudentProfileResponse>
//            ) {
//                val resp=response.body()
//                tvStudentName.text=resp?.name
//                tvStudentEmail.text=resp?.email
//                tvStudentDesignation.text=resp?.designation
//                tvStudentInstitution.text=resp?.institution
//
//                val link="https://appbackend-100.herokuapp.com/api/users/view-profilepic/${args.id}"
//                Picasso.get()
//                    .load(link)
//                    .placeholder(R.drawable.default_avatar)
//                    .error(R.drawable.default_avatar)
//                    .into(civStudent)
//            }
//
//            override fun onFailure(call: Call<StudentProfileResponse>, t: Throwable) {
//                println("student profile fetch failed")
//            }
//
//        })
//    }
//
//
//}