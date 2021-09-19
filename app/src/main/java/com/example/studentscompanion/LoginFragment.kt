package com.example.studentscompanion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.studentscompanion.response.AuthResponse
import com.example.studentscompanion.util.ProgressDialogBox
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    val TAG="LOGIN FRAGMENT"
    var auth:String?=null
    private lateinit var progressDialogBox: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogIn.setOnClickListener {

            val id=etUserId.text.toString()
            val password=etPassword.text.toString()

            if(id.isEmpty() || password.isEmpty() || (id.isEmpty() && password.isEmpty())){

                Toast.makeText(requireContext(), "Either or both fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                progressDialogBox= ProgressDialogBox.showDialog(requireContext(),"Logging you in..",false)
                progressDialogBox.show()
                val requestBody: MutableMap<String, String> = HashMap()
                requestBody["userId"] = id
                requestBody["password"] = password
                getAuth(requestBody)
            }

        }

    }

    private fun getAuth(requestBody: MutableMap<String, String>) {
        val auth=RetrofitBuilder.apiService.getAuth(requestBody)
        auth.enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d(TAG,response.body().toString())
                val obj=response.body()
                val token=obj?.token
                Log.d(
                    TAG,
                    "TOKEN = $token"
                )
                if(progressDialogBox.isShowing){
                    progressDialogBox.dismiss()
                }

                if(token!=null){
                    val action=LoginFragmentDirections.actionLoginFragmentToHomeActivity2(obj!!)
                    findNavController().navigate(action)
                }
                else{
                    ProgressDialogBox.dialogWithPosNegBtn(requireContext(),"Login Failed. Please try again",true)

                        .setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }


            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d(
                    TAG,
                    "Auth Failed"
                )
                progressDialogBox.dismiss()

                    ProgressDialogBox.dialogWithPosNegBtn(requireContext(),"Login Failed. Please try again",true)

                        .setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()

            }

        })
    }

}