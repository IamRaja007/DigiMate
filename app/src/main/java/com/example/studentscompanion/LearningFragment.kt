package com.example.studentscompanion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentscompanion.adapters.LearningAdapter
import com.example.studentscompanion.adapters.QueryAdapter
import com.example.studentscompanion.response.DocsItem
import com.example.studentscompanion.response.GetAllDocsResponse
import com.example.studentscompanion.response.QueriesItems
import com.example.studentscompanion.response.ReplyItems
import com.example.studentscompanion.util.ProgressDialogBox
import com.example.studentscompanion.util.RvItemDecorator
import kotlinx.android.synthetic.main.fragment_learning.*
import kotlinx.android.synthetic.main.fragment_proctoring.*
import kotlinx.android.synthetic.main.fragment_proctoring.rvSeeUnderProctorList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LearningFragment : Fragment(), LearningAdapter.ItemSeeRepliesDocsClick {

    var auth: String? = null
    var designation: String? = null
    private lateinit var progressDialogBox: AlertDialog
    val listOfQueries: ArrayList<DocsItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialogBox= ProgressDialogBox.showDialog(requireContext(),"Loading..",false)
        progressDialogBox.show()

        val pref = context?.getSharedPreferences("Mytoken", 0)
        auth = pref?.getString("auth", "")
        designation = pref?.getString("designation", "")

        if(designation == "Faculty"){
            fabWriteDoc.visibility=View.VISIBLE
            fabWriteDoc.setOnClickListener {
                findNavController().navigate(R.id.action_learningFragment_to_addDocFragment)
            }
        }
        else{
            fabWriteDoc.visibility=View.GONE
        }

        val request=RetrofitBuilder.apiService.getAllDocs(auth!!)
        request.enqueue(object : Callback<GetAllDocsResponse>{
            override fun onResponse(
                call: Call<GetAllDocsResponse>,
                response: Response<GetAllDocsResponse>
            ) {
                val body=response.body()
                println("docs success ${body?.docs}")

                for (i in body?.docs!!) {
                    if(!listOfQueries.contains(i))
                    listOfQueries.add(i!!)
                }
                progressDialogBox.dismiss()
                setUpQueriesRecyclerView()

            }

            override fun onFailure(call: Call<GetAllDocsResponse>, t: Throwable) {
                println("docs fetch failed")
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

    private fun setUpQueriesRecyclerView() {
        val itemDecorator = RvItemDecorator(20)
        val rvAdapter =
            LearningAdapter(requireContext(), listOfQueries, this)
        rvSeeLearningList.addItemDecoration(itemDecorator)
        rvSeeLearningList.adapter = rvAdapter
        rvSeeLearningList.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onSeeRepliesDocsClick(repliesList: List<ReplyItems>?, pathId: String) {
        SeeRepliesBottomSheet(repliesList as ArrayList<ReplyItems>?, pathId,true).show(
            requireActivity().supportFragmentManager, ""
        )
    }

}