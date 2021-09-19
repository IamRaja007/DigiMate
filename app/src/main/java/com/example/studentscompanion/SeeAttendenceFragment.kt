package com.example.studentscompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentscompanion.adapters.AttendanceAdapter
import com.example.studentscompanion.adapters.SeeAttendanceAdapter
import com.example.studentscompanion.response.AttendeesItem
import com.example.studentscompanion.response.GetAttendenceResponse
import com.example.studentscompanion.util.RvItemDecorator
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_attendance.*
import kotlinx.android.synthetic.main.fragment_see_attendence.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SeeAttendenceFragment : Fragment() {

    val args:SeeAttendenceFragmentArgs by navArgs()
    val list:ArrayList<AttendeesItem> = ArrayList()
    var auth:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_see_attendence, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bottomNavView.visibility=View.GONE
        println("print ${args.id}")
        val pref=context?.getSharedPreferences("Mytoken",0)
        auth=pref?.getString("auth","")
        getAttendenceList()

    }

    fun getAttendenceList(){
        val request=RetrofitBuilder.apiService.getAttendence(args.id,auth!!)

        request.enqueue(object :retrofit2.Callback<GetAttendenceResponse>{
            override fun onResponse(
                call: Call<GetAttendenceResponse>,
                response: Response<GetAttendenceResponse>
            ) {
                val body=response.body()
                for(i in body?.attendees!!){
                    list.add(i!!)
                }
                setUpRecyclerView()
            }

            override fun onFailure(call: Call<GetAttendenceResponse>, t: Throwable) {
                println("failed")
            }

        })
    }

    private fun setUpRecyclerView() {
        val itemDecorator = RvItemDecorator(20)
        val rvAdapter =
            SeeAttendanceAdapter(requireContext(),list)
        rvSeeAttendence.addItemDecoration(itemDecorator)
        rvSeeAttendence.adapter = rvAdapter
        rvSeeAttendence.layoutManager = LinearLayoutManager(requireContext())
    }
}