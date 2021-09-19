package com.example.studentscompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentscompanion.adapters.QueryAdapter
import com.example.studentscompanion.adapters.SeeAttendanceAdapter
import com.example.studentscompanion.adapters.StudentsUnderProctorAdapter
import com.example.studentscompanion.response.*
import com.example.studentscompanion.util.ProgressDialogBox
import com.example.studentscompanion.util.RvItemDecorator
import com.example.studentscompanion.util.StudentClick
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_proctoring.*
import kotlinx.android.synthetic.main.fragment_see_attendence.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProctoringFragment : Fragment(), StudentClick, QueryAdapter.ItemSeeRepliesClick {

    var designation: String? = null
    var navigated: Boolean = false
    var auth: String? = null
    private lateinit var progressDialogBox: AlertDialog
    val listOfQueries: ArrayList<QueriesItems> = ArrayList()
    val list: ArrayList<ProctoringItemss> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proctoring, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialogBox = ProgressDialogBox.showDialog(requireContext(), "Loading..", false)
        progressDialogBox.show()
        val pref = context?.getSharedPreferences("Mytoken", 0)
        designation = pref?.getString("designation", "")
        auth = pref?.getString("auth", "")

        if (designation == "Faculty") {
            rvSeeUnderProctorList.visibility = View.VISIBLE

            if (!navigated) {
                val request = RetrofitBuilder.apiService.getAllQueries(auth!!)
                request.enqueue(object : Callback<GetAllQueriesResponse> {
                    override fun onResponse(
                        call: Call<GetAllQueriesResponse>,
                        response: Response<GetAllQueriesResponse>
                    ) {
                        progressDialogBox.dismiss()

                        val body = response.body()

                        println(body.toString())

                        for (i in body?.queries!!) {
                            if(!listOfQueries.contains(i!!)){
                                listOfQueries.add(i!!)
                            }
                        }
                        setUpQueriesRecyclerView()
                    }

                    override fun onFailure(call: Call<GetAllQueriesResponse>, t: Throwable) {
                        println("queries failed")
                        progressDialogBox.dismiss()
                        ProgressDialogBox.dialogWithPosNegBtn(
                            requireContext(),
                            "Error occured. Please try again",
                            true
                        )

                            .setNegativeButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }

                })

            } else {
                setUpQueriesRecyclerView()
            }


        } else {

            rvSeeUnderProctorList.visibility = View.VISIBLE
            fabWriteAQuery.visibility = View.VISIBLE

            fabWriteAQuery.setOnClickListener {
                findNavController().navigate(R.id.action_proctoringFragment_to_addQueryFragment)
            }

            println(auth)
            val request = RetrofitBuilder.apiService.getAllQueries(auth!!)
            request.enqueue(object : Callback<GetAllQueriesResponse> {
                override fun onResponse(
                    call: Call<GetAllQueriesResponse>,
                    response: Response<GetAllQueriesResponse>
                ) {
                    val body = response.body()
                    progressDialogBox.dismiss()

                    println(body.toString())



                    for (i in body?.queries!!) {
                        if(!listOfQueries.contains(i!!)){
                            listOfQueries.add(i)
                        }
                    }
                    setUpQueriesRecyclerView()
                }

                override fun onFailure(call: Call<GetAllQueriesResponse>, t: Throwable) {
                    println("queries failed")
                    progressDialogBox.dismiss()
                    ProgressDialogBox.dialogWithPosNegBtn(
                        requireContext(),
                        "Error occured. Please try again",
                        true
                    )

                        .setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }

            })
//            val requestDetails = RetrofitBuilder.apiService.getMyProctorProfile(auth!!)
//            requestDetails.enqueue(object : Callback<GetMyProctorProfileResponse> {
//                override fun onResponse(
//                    call: Call<GetMyProctorProfileResponse>,
//                    response: Response<GetMyProctorProfileResponse>
//                ) {
//                    rvSeeUnderProctorList.visibility = View.GONE
//                    CLProctorDetails.visibility = View.VISIBLE
//                    tvProctorDesignation.text = designation
//                    tvProctorEmail.text = response.body()?.email
//                    tvProctorInstitution.text = response.body()?.institution
//                    val link =
//                        "https://appbackend-100.herokuapp.com/api/users/view-profilepic/${response.body()?.id}"
//                    Picasso.get()
//                        .load(link)
//                        .placeholder(R.drawable.default_avatar)
//                        .error(R.drawable.default_avatar)
//                        .into(civProctor)
//
//                    tvProctorName.text = response.body()?.name
//
//                }
//
//                override fun onFailure(call: Call<GetMyProctorProfileResponse>, t: Throwable) {
//                    println("proctor failed")
//                }
//
//            })


        }
    }

//    private fun setUpRecyclerView() {
//        val itemDecorator = RvItemDecorator(20)
//        val rvAdapter =
//            StudentsUnderProctorAdapter(requireContext(), list, this)
//        rvSeeUnderProctorList.addItemDecoration(itemDecorator)
//        rvSeeUnderProctorList.adapter = rvAdapter
//        rvSeeUnderProctorList.layoutManager = LinearLayoutManager(requireContext())
//    }

    private fun setUpQueriesRecyclerView() {
        val itemDecorator = RvItemDecorator(20)
        val rvAdapter =
            QueryAdapter(requireContext(), listOfQueries, this)
        rvSeeUnderProctorList.addItemDecoration(itemDecorator)
        rvSeeUnderProctorList.adapter = rvAdapter
        rvSeeUnderProctorList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStudentClick(Id: String) {
        navigated = true
        val action =
            ProctoringFragmentDirections.actionProctoringFragmentToSeeStudentProfileFragment(Id)
        findNavController().navigate(action)
    }

    override fun onSeeRepliesClick(repliesList: List<ReplyItems>?, pathId: String) {
        println("idhar aaya")
        SeeRepliesBottomSheet(repliesList as ArrayList<ReplyItems>?, pathId).show(
            requireActivity().supportFragmentManager, ""
        )
    }
}