package com.example.studentscompanion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.studentscompanion.response.CreateClassResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_sheet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateClassBottomSheet(val id:String,private val interaction: addClass? = null):
    BottomSheetDialogFragment() {

    var className: String? = null
    var classLink: String? = null
    var time:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (change) {
//            tvShowFieldNameBottomSheet.text = getString(R.string.enterStatus)
//            etBottomSheet.hint = "Enter your status"
//
//            btnBottomSheetSave.setOnClickListener {
//                newStatus = etBottomSheet.text.toString()
//
//                database.collection("users").document(id).update(
//                    mapOf("status" to newStatus)
//                ).addOnSuccessListener {
//                    interaction?.setTextToField(newStatus!!, "STATUS")
//                }
//
//
//            }
//        } else {
//            btnBottomSheetSave.setOnClickListener {
//                newName = etBottomSheet.text.toString()
//
//                database.collection("users").document(id).update(
//                    mapOf("name" to newName)
//                ).addOnSuccessListener {
//                    interaction?.setTextToField(newName!!, "NAME")
//                }
//            }

        btnBottomSheetCreate.setOnClickListener {
            classLink = etBottomSheetMeetingLink.text.toString()
            className = etBottomSheetClassName.text.toString()
            time= etBottomSheetMeetingTime.text.toString()

            val pref=context?.getSharedPreferences("Mytoken",0)
            val auth=pref?.getString("auth","")

            val requestBody: MutableMap<String, String> = HashMap()
            requestBody["classLink"] = classLink!!
            requestBody["topic"] = className!!
            requestBody["time"] = time!!

            val request=RetrofitBuilder.apiService.createClass(requestBody,auth!!)
            request.enqueue(object : Callback<CreateClassResponse>{
                override fun onResponse(
                    call: Call<CreateClassResponse>,
                    response: Response<CreateClassResponse>
                ) {
                    val body=response.body()
                    interaction?.setTextToField(body?.date!!,body.topic!!,body.facultyName!!,body.time!!,body.facultyId!!)
                    dismiss()
                }

                override fun onFailure(call: Call<CreateClassResponse>, t: Throwable) {

                }

            })

        }

        btnBottomSheetCancel.setOnClickListener {
            dismiss()
        }
    }

    interface addClass {
        fun setTextToField(date: String, topic: String,facultyName:String,time:String,facultyId:String)
    }

    override fun dismiss() {
        super.dismiss()
    }
}
