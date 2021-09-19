package com.example.studentscompanion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentscompanion.adapters.AttendanceAdapter
import com.example.studentscompanion.response.ClassesItem
import com.example.studentscompanion.response.GetClassesResponse
import com.example.studentscompanion.util.ClickInterface
import com.example.studentscompanion.util.ProgressDialogBox
import com.example.studentscompanion.util.RvItemDecorator
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_attendance.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AttendanceFragment : Fragment(),ClickInterface,CreateClassBottomSheet.addClass {

    var designation:String?=null
    var id:String?=null
    private lateinit var progressDialogBox: AlertDialog
    var classesList:ArrayList<ClassesItem> = ArrayList()
    var rvAdapter:AttendanceAdapter?=null
    var auth:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref=context?.getSharedPreferences("Mytoken",0)
        println("yo : ${pref?.getString("auth","")}")

        designation=pref?.getString("designation","")
        id=pref?.getString("userId","")
        auth=pref?.getString("auth","")
        println("userId in Attendence Fragment : ${pref?.getString("userId","")}")
        initViews()
        getAllClasses()

    }

    private fun getAllClasses() {
        val auth=RetrofitBuilder.apiService.getAllClasses()
        classesList.clear()
        auth.enqueue(object:Callback<GetClassesResponse>{
            override fun onResponse(
                call: Call<GetClassesResponse>,
                response: Response<GetClassesResponse>
            ) {

                progressDialogBox.dismiss()
                val body=response.body()

                for(i in body?.classes!!){
                    println(i.toString())
                    classesList.add(i!!)
                }

                setUpRecyclerView()
            }

            override fun onFailure(call: Call<GetClassesResponse>, t: Throwable) {
//                classesList.add(null)
                progressDialogBox.dismiss()
                ProgressDialogBox.dialogWithPosNegBtn(requireContext(),"Error occured. Please try again",true)

                    .setNegativeButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }

        })


    }

    private fun setUpRecyclerView() {
        println("Body rv= ${classesList.toString()}")
        println("id rv= ${id}")

        val itemDecorator = RvItemDecorator(20)
        rvAdapter =
            AttendanceAdapter(requireContext(),classesList,id!!,this)
        rvShowClasses.addItemDecoration(itemDecorator)
        rvShowClasses.adapter = rvAdapter
        rvShowClasses.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViews() {
        progressDialogBox= ProgressDialogBox.showDialog(requireContext(),"Logging you in..",false)
        progressDialogBox.show()
        if(designation == "Faculty"){
            btnCreateNewClass.visibility=View.VISIBLE

            btnCreateNewClass.setOnClickListener {
                CreateClassBottomSheet(id =id!!,this ).show(
                    requireActivity().supportFragmentManager,""
                )
            }
        }
        else{
            btnCreateNewClass.visibility=View.GONE
        }
    }

    override fun onJoinClassClick(position: Int, classLink: String, classId:String) {
//        println("I am clicked Joinid=$classId")
        println("auth = $auth")
        println("class id = $classId")
        val request=RetrofitBuilder.apiService.attendClass(classId,auth!!)

        request.enqueue(object:Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                println("attendence Done ${response.message()}")
                var link:String?=null

                link = if(classLink.contains("https://")){
                    classLink
                } else{
                    "https://$classLink"
                }

                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(browserIntent)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                println("attendence Failed ${t.message}")
            }

        })


        //Join class
    }

    override fun onGetAttendanceClick(position: Int, classId: String) {
        val action=AttendanceFragmentDirections.actionAttendanceFragmentToSeeAttendenceFragment(classId)
        findNavController().navigate(action)
//        findNavController().navigate(R.id.action_attendanceFragment_to_seeAttendenceFragment)

    }


    override fun setTextToField(date: String, topic: String, facultyName: String, time: String,facultyId:String) {
        val newClass=ClassesItem(date,facultyId,"",0,topic,"","",time,facultyName)
        classesList.add(newClass)
        rvAdapter?.notifyItemInserted(classesList.size-1)
        rvShowClasses.scrollToPosition(classesList.size-1)
    }

}