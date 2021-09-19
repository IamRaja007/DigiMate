package com.example.studentscompanion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    val args: HomeActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val pref=applicationContext.getSharedPreferences("Mytoken",0)
        val editor=pref.edit()
        editor.putString("auth",args.auth.token)
        editor.putString("designation",args.auth.user?.designation)
        editor.putString("userId",args.auth.user?.userId)
        editor.putString("realId",args.auth.user?.id)
        editor.putString("name",args.auth.user?.name)
        println("userId in HomeActivity= ${args.auth.user?.userId}")
        editor.apply()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerHome) as NavHostFragment
        val navController = navHostFragment.navController

//        val bundle = Bundle()
//        bundle.putString("tokeen",args.token)
        bottomNavView.setupWithNavController(navController)
//        navController.setGraph(R.navigation.nav_graph,bundle)


        println("HomeActivity : ${args.auth.toString()}")
    }
}