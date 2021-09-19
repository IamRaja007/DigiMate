package com.example.studentscompanion

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.example.studentscompanion.response.CreateQueryResponse
import com.example.studentscompanion.response.CreateQueryWithPdfResponse
import com.example.studentscompanion.util.ProgressDialogBox
import kotlinx.android.synthetic.main.fragment_add_query.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


private const val PICK_IMAGE_REQUEST_CODE=111

class AddQueryFragment : Fragment() {

    var auth:String?=null
    var fileCheck:String?=null
    var bodyOfFile:MultipartBody.Part?=null
    var fileType:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_query, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref=requireActivity().getSharedPreferences("Mytoken",0)
        auth=pref.getString("auth","")

        btnCancel.setOnClickListener {
            activity?.onBackPressed()
        }

        btnCreate.setOnClickListener {
            val textQuery=etQuery.text.toString()

            var requesttext: RequestBody?=null

            val sdf = SimpleDateFormat("hh:mm")
            val currentDate = sdf.format(Date())
            System.out.println(" C DATE is  "+currentDate)

            val requestTime = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), currentDate)



            if(textQuery.isNotEmpty()){
                requesttext = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), textQuery)
            }

            if(requesttext != null && fileCheck!=null && fileType?.contains("image/*") == true){

                val upload=RetrofitBuilder.apiService.createQueryWithTextPhoto(auth!!,bodyOfFile,requesttext,requestTime)
                upload.enqueue(object :retrofit2.Callback<CreateQueryResponse>{

                    override fun onResponse(
                        call: Call<CreateQueryResponse>,
                        response: Response<CreateQueryResponse>
                    ) {
                        etQuery.text.clear()
                        activity?.onBackPressed()
                        println("upload success for both text and image")
                    }

                    override fun onFailure(call: Call<CreateQueryResponse>, t: Throwable) {
                        println("upload failed for both text and image")
                    }

                })

            }
            else if(requesttext != null && fileCheck!=null && fileType?.contains("image/*") == false){
                val upload=RetrofitBuilder.apiService.createQueryWithTextPdf(auth!!,bodyOfFile,requesttext,requestTime)
                upload.enqueue(object :retrofit2.Callback<CreateQueryWithPdfResponse>{

                    override fun onResponse(
                        call: Call<CreateQueryWithPdfResponse>,
                        response: Response<CreateQueryWithPdfResponse>
                    ) {
                        etQuery.text.clear()
                        activity?.onBackPressed()
                        println("upload success for both text and pdf")
                    }

                    override fun onFailure(call: Call<CreateQueryWithPdfResponse>, t: Throwable) {
                        println("upload failed for both text and pdf")
                    }

                })
            }

            else if(requesttext != null && fileCheck==null){
                val upload=RetrofitBuilder.apiService.createQueryWithTextPhoto(auth!!,queryText = requesttext,queryTime = requestTime)
                upload.enqueue(object :retrofit2.Callback<CreateQueryResponse>{

                    override fun onResponse(
                        call: Call<CreateQueryResponse>,
                        response: Response<CreateQueryResponse>
                    ) {
                        etQuery.text.clear()
                        activity?.onBackPressed()
                        println("upload success with text only")
                    }

                    override fun onFailure(call: Call<CreateQueryResponse>, t: Throwable) {
                        println("upload failed with text only")
                    }

                })
            }
            else if(requesttext == null && fileCheck !=null && fileType?.contains("image/*") == true){

                val upload=RetrofitBuilder.apiService.createQueryWithTextPhoto(auth!!,image = bodyOfFile,queryTime = requestTime)
                upload.enqueue(object :retrofit2.Callback<CreateQueryResponse>{

                    override fun onResponse(
                        call: Call<CreateQueryResponse>,
                        response: Response<CreateQueryResponse>
                    ) {

                        activity?.onBackPressed()
                        println("upload success for only image")
                    }

                    override fun onFailure(call: Call<CreateQueryResponse>, t: Throwable) {
                        println("upload failed for only image")
                    }

                })
            }
            else if(requesttext == null && fileCheck !=null && fileType?.contains("image/*") == false){
                val upload=RetrofitBuilder.apiService.createQueryWithTextPdf(auth!!,filePdf = bodyOfFile,queryTime = requestTime)
                upload.enqueue(object :retrofit2.Callback<CreateQueryWithPdfResponse>{

                    override fun onResponse(
                        call: Call<CreateQueryWithPdfResponse>,
                        response: Response<CreateQueryWithPdfResponse>
                    ) {

                        activity?.onBackPressed()
                        println("upload success for only pdf")
                    }

                    override fun onFailure(call: Call<CreateQueryWithPdfResponse>, t: Throwable) {
                        println("upload failed for only pdf ${t.localizedMessage}")
                    }

                })
            }

//            if(requesttext != null && fileCheck !=null){
//                println("requesttext != null && fileCheck !=null ---- requestText=$requesttext and fileCheck=$fileCheck")
//
//                val upload=RetrofitBuilder.apiService.createQueryWithTextPhoto(auth!!,bodyOfFile,requesttext)
//                upload.enqueue(object :retrofit2.Callback<CreateQueryResponse>{
//
//                    override fun onResponse(
//                        call: Call<CreateQueryResponse>,
//                        response: Response<CreateQueryResponse>
//                    ) {
//                        println("upload success for both text and file")
//                    }
//
//                    override fun onFailure(call: Call<CreateQueryResponse>, t: Throwable) {
//                        println("upload failed for both text and file")
//                    }
//
//                })
//            }
//            else if(requesttext != null && fileCheck ==null){
//                println("requesttext != null && fileCheck ==null ---- requestText=$requesttext and fileCheck=$fileCheck")
//                val upload=RetrofitBuilder.apiService.createQueryWithTextPhoto(auth!!,queryText = requesttext)
//                upload.enqueue(object :retrofit2.Callback<CreateQueryResponse>{
//
//                    override fun onResponse(
//                        call: Call<CreateQueryResponse>,
//                        response: Response<CreateQueryResponse>
//                    ) {
//                        println("upload success with text only")
//                    }
//
//                    override fun onFailure(call: Call<CreateQueryResponse>, t: Throwable) {
//                        println("upload failed with text only")
//                    }
//
//                })
//            }
//            else if(requesttext == null && fileCheck !=null){
//                println("requesttext == null && fileCheck !=null ---- requestText=$requesttext and fileCheck=$fileCheck and $bodyOfFile")
//                val upload=RetrofitBuilder.apiService.createQueryWithTextPhoto(auth!!,image = bodyOfFile)
//                upload.enqueue(object :retrofit2.Callback<CreateQueryResponse>{
//
//                    override fun onResponse(
//                        call: Call<CreateQueryResponse>,
//                        response: Response<CreateQueryResponse>
//                    ) {
//                        println("upload success with file only")
//                    }
//
//                    override fun onFailure(call: Call<CreateQueryResponse>, t: Throwable) {
//                        println("upload failed with file")
//                    }
//
//                })
//            }

        }

        btnAddPhotoOrPdf.setOnClickListener {
            ProgressDialogBox.dialogWithPosNegBtn(requireContext(),"Choose Pdf Or Photo",true).
                    setPositiveButton("Pdf"){_,_ ->

//                        if(!checkPermission()){
//                            requestPermission()
//                        }
//                        else{
//                            pickImageFromGallery()
//                        }
                        checkPermissions()
                        fileType="application/pdf"


                    }
                .setNegativeButton("Photo"){ _,_ ->

                    fileType="image/*"
//                    if(!checkPermission()){
//                        requestPermission()
//                    }
//                    else{
//                        pickImageFromGallery()
//                    }
                    checkPermissions()


                }
//                .setNegativeButton("Cancel"){dialog,_ ->
//                    dialog.dismiss()
//                }
                .create()
                .show()
        }
    }

    private fun checkPermissions() {
        if((checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED)
            && (checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED)){

            val permissionWrite= arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val permissionRead= arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

            requestPermissions(permissionRead,100)//Providing an request code
            requestPermissions(permissionWrite,101)//Providing an request code


        }
        else{
            pickImageFromGallery()
        }
    }

//    private fun checkPermission():Boolean {
//            if (SDK_INT >= Build.VERSION_CODES.R) {
//                return Environment.isExternalStorageManager();
//            } else {
//                val result = ContextCompat.checkSelfPermission(requireActivity(), READ_EXTERNAL_STORAGE);
//                val result1 = ContextCompat.checkSelfPermission(requireActivity(), WRITE_EXTERNAL_STORAGE);
//                return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
//            }
//        }

//    private fun requestPermission() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            try {
//                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
//                intent.addCategory("android.intent.category.DEFAULT")
//                intent.data =
//                    Uri.parse(
//                        context?.applicationContext?.let {
//                            String.format(
//                                "package:%s",
//                                it.packageName
//                            )
//                        }
//                    )
//                startActivityForResult(intent, 2296)
//            } catch (e: Exception) {
//                val intent = Intent()
//                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
//                startActivityForResult(intent, 2296)
//            }
//        } else {
//            //below android 11
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(WRITE_EXTERNAL_STORAGE),
//                PICK_IMAGE_REQUEST_CODE
//            )
//        }
//    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST_CODE){
//            data?.data.let { imageURI ->
////
//                uploadImage(imageURI)
//                println("Printing imageURI : $imageURI")
//            }
//        }
//        else if (requestCode == 2296) {
//            if (SDK_INT >= Build.VERSION_CODES.R) {
//                if (Environment.isExternalStorageManager()) {
//                    // perform action when allow permission success
//                    pickImageFromGallery()
//                } else {
//                    Toast.makeText(context, "Allow permission for storage access!", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        }
//    }

    private fun pickImageFromGallery() {
        var intent:Intent?=null

        if(fileType?.contains("image/*") == true){
            intent= Intent(Intent.ACTION_PICK)
        }
        else{
            intent=Intent(Intent.ACTION_PICK)
        }
//       intent= Intent(Intent.ACTION_PICK)
//        intent.type= "image/*"
        intent.type=fileType

        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST_CODE){
            data?.data.let { imageURI ->
//
                uploadImage(imageURI)
                println("Printing imageURI : $imageURI")
            }
        }

    }

    private fun uploadImage(imageURI: Uri?) {


        println("pdf file uri $imageURI")
            val file = imageURI?.let {
                uriToImageFile(it)
            }

        println("pdf file path $file")

        if(imageURI!=null){
            fileCheck="Done"
            tvFileAttachedOrNot.visibility=View.VISIBLE
        }

var body:MultipartBody.Part ?=null
        if(fileType?.contains("image/*")!!){
            println(" image multipart")
            val requestFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file!!)

            body= MultipartBody.Part.createFormData("photo", file.name, requestFile)

        }
        else{
            println(" pdf multipart")
            val requestFile: RequestBody = RequestBody.create("application/pdf".toMediaTypeOrNull(), file!!)

            body= MultipartBody.Part.createFormData("pdf", file.name, requestFile)

        }

        bodyOfFile = body
//        val upload=RetrofitBuilder.apiService.createQueryWithTextPhoto(auth!!,body)
//        upload.enqueue(object :retrofit2.Callback<CreateQueryResponse>{
//
//            override fun onResponse(
//                call: Call<CreateQueryResponse>,
//                response: Response<CreateQueryResponse>
//            ) {
//                println("upload success")
//            }
//
//            override fun onFailure(call: Call<CreateQueryResponse>, t: Throwable) {
//                println("upload failed")
//            }
//
//        })
    }


    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }

}