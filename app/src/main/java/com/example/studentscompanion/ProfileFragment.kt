package com.example.studentscompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentscompanion.adapters.StudentsUnderProctorAdapter
import com.example.studentscompanion.response.ProctoringItemss
import com.example.studentscompanion.response.StudentProfileResponse
import com.example.studentscompanion.response.StudentsUnderResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_see_student_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    var id:String?=null
    var auth:String?=null
    var designation:String?=null
    var list:ArrayList<ProctoringItemss> =ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref=context?.getSharedPreferences("Mytoken",0)
        id=pref?.getString("realId","")
        auth=pref?.getString("auth","")
        designation = pref?.getString("designation", "")
        println("profile id = $id")

        if(designation == "Faculty"){
            tvHeadingList.visibility=View.VISIBLE
            rvshowUnderStudents.visibility=View.VISIBLE
            CLStudentProfile.visibility=View.GONE

            val request=RetrofitBuilder.apiService.getProctorProfile(auth!!)
            request.enqueue(object:Callback<StudentsUnderResponse>{
                override fun onResponse(
                    call: Call<StudentsUnderResponse>,
                    response: Response<StudentsUnderResponse>
                ) {
                    val body=response.body()

                    tvProctorName.text =body?.user?.name
                    tvProctorEmail.text = body?.user?.email
                    tvProctorDesignation.text =body?.user?.designation
                    tvProctorInstitution.text =body?.user?.institution

                    val link2="https://appbackend-100.herokuapp.com/api/users/view-profilepic/${body?.user?.id}"
                    Picasso.get()
                        .load(link2)
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .into(civProctor)

                    for(i in body?.user?.proctoring!!){
                        if(!list.contains(i))
                        list.add(i!!)
                    }
                    setUpRecyclerView()
                }

                override fun onFailure(call: Call<StudentsUnderResponse>, t: Throwable) {
                    println("proctor profile fetched failed")
                }

            })

        }
        else{
            val request=RetrofitBuilder.apiService.getStudentProfile(auth!!)
            request.enqueue(object : Callback<StudentProfileResponse> {
                override fun onResponse(
                    call: Call<StudentProfileResponse>,
                    response: Response<StudentProfileResponse>
                ) {
                    val resp=response.body()
                    tvProfileName.text=resp?.user?.name
                    tvProfileEmail.text=resp?.user?.email
                    tvProfileDesignation.text=resp?.user?.designation
                    tvProfileInstitution.text=resp?.user?.institution

                    tvProctorName.text =resp?.proctor?.name
                    tvProctorEmail.text = resp?.proctor?.email
                    tvProctorDesignation.text =resp?.proctor?.designation
                    tvProctorInstitution.text =resp?.proctor?.institution

                    val link2="https://appbackend-100.herokuapp.com/api/users/view-profilepic/${resp?.proctor?.id}"
                    Picasso.get()
                        .load(link2)
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .into(civProctor)

                    val link="https://appbackend-100.herokuapp.com/api/users/view-profilepic/${id}"
                    Picasso.get()
                        .load(link)
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .into(civProfile)
                }

                override fun onFailure(call: Call<StudentProfileResponse>, t: Throwable) {
                    println("student profile fetch failed")
                }

            })
        }


    }

    private fun setUpRecyclerView() {
        val adapter=StudentsUnderProctorAdapter(requireContext(),list)
        rvshowUnderStudents.adapter=adapter

        rvshowUnderStudents.layoutManager =LinearLayoutManager(requireContext())
    }

}