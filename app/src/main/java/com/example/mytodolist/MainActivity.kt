package com.example.mytodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytodolist.ui.view.ListView
import com.example.mytodolist.util.kmp.StringKMP

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val stringKMP = StringKMP("")
    println("(MainActivity) Is ABABA has AB? ${stringKMP.kmp("ABABA")}")
    println("(MainActivity) Is AAAAA has AB? ${stringKMP.kmp("AAAAA")}")
    println("(MainActivity) Is ACAAB has AB? ${stringKMP.kmp("ACAAB")}")
    setContent {
      // 네비게이션 컨트롤러 선언
      val navController = rememberNavController()
      NavHost( // 경로 지정
        navController = navController, // 컨트롤러
        startDestination = "listView" // 시작 화면의 route명
      ) {
        composable("listView") { ListView() } // 화면과 route명을 연결
      }
    }
  }
}