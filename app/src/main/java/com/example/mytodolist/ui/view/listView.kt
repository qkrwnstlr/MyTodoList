package com.example.mytodolist.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.mytodolist.model.ListData
import com.example.mytodolist.ui.viewmodel.ListViewModel

@Composable
fun ListView(viewModel: ListViewModel = ListViewModel()) {
  println("ListView is created")
  val listDataList = viewModel.getAllListData()
  Column {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    ) {
      items(items = listDataList) { listData ->
        ListItem(listData)
      }
    }
  }
}

// TODO : ViewModel, Repository 계속 생성됨 해결 필요 -> singleton?
@Composable
fun ListItem(listData: ListData, viewModel: ListViewModel = ListViewModel()) {
  println("ListItem${listData.no} is created")
  val (isChecked, onCheckedChange) = mutableStateOf(false)
  Row {
    Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
    Text(text = "${listData.no}")
    Text(text = listData.todo, textDecoration = TextDecoration.LineThrough)
    Button(onClick = { viewModel.changeFinishState(listData.no) }) {
      Text(text = "완료")
    }
  }
}

@Composable
fun SearchBar(viewModel: ListViewModel = ListViewModel()) {
  println("SearchBar is created")
}