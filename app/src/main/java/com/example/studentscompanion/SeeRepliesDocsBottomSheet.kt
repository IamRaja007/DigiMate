package com.example.studentscompanion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentscompanion.adapters.RepliesRecyclerAdapter
import com.example.studentscompanion.response.CreateClassResponse
import com.example.studentscompanion.response.DocsItem
import com.example.studentscompanion.response.ReplyItems
import com.example.studentscompanion.response.WriteReplyResponse
import com.example.studentscompanion.util.RvItemDecorator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_sheet.*
import kotlinx.android.synthetic.main.see_replies_bottom_sheet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class SeeRepliesDocsBottomSheet(val list:ArrayList<DocsItem>?, val pathId:String):
//    BottomSheetDialogFragment() {
//
//    var auth:String?=null
//    var name:String?=null
//    var realId:String?=null
//    lateinit var rvAdapter: RepliesRecyclerAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.see_replies_bottom_sheet, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setUpRecyclerView()
//        val pref=context?.getSharedPreferences("Mytoken",0)
//        auth=pref?.getString("auth","")
//        name=pref?.getString("name","")
//        realId=pref?.getString("realId","")
//        println("SeeRepliesBottomSheet View Created")
//
////        if(list!=null)
////        setUpRecyclerView()
//
//        ivsendReplybtn.setOnClickListener {
//            println("insided onclicklistener = $pathId")
//            val replyWritten = etWriteReply.text.toString()
//            etWriteReply.text.clear()
//            val requestBody: MutableMap<String, String> = HashMap()
//            requestBody["text"] = replyWritten
//
//            val request = RetrofitBuilder.apiService.writeReply(auth!!, pathId, requestBody)
//            request.enqueue(object : Callback<WriteReplyResponse> {
//                override fun onResponse(
//                    call: Call<WriteReplyResponse>,
//                    response: Response<WriteReplyResponse>
//                ) {
//                    println("Write Success")
//                    list?.add(ReplyItems(name,"",replyWritten,realId))
//                    tvNoComments.visibility=View.GONE
//                    rvAdapter.notifyDataSetChanged()
//                }
//
//                override fun onFailure(call: Call<WriteReplyResponse>, t: Throwable) {
//                    println("Write Failed")
//                }
//
//            })
//        }
//        if(list?.size == 0){
//            tvNoComments.visibility=View.VISIBLE
//        }
//        else{
//            tvNoComments.visibility=View.GONE
//        }
//
//        tvCommentsHeading.setOnClickListener {
//            dismiss()
//        }
//
//    }
//
//    private fun setUpRecyclerView() {
//        println("Setting Recycler View")
//        rvAdapter= list?.let { RepliesRecyclerAdapter(requireContext(), it) }!!
//        rvSeeReplies.addItemDecoration(RvItemDecorator(20))
//        rvSeeReplies.adapter=rvAdapter
//        rvSeeReplies.layoutManager =LinearLayoutManager(requireContext())
//    }
//
//    override fun dismiss() {
//        super.dismiss()
//    }
//}
