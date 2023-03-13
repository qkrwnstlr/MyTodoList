package com.example.mytodolist.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytodolist.model.ListData
import com.example.mytodolist.ui.viewmodel.ListViewModel

@Composable
fun ListView() {
  val viewModel = viewModel<ListViewModel>()
  ConstraintLayout(Modifier.fillMaxSize()) {
    val (listLayout1) = createRefs() // 제약에 사용할 참조 생성
    ListLayout(
      Modifier
        .constrainAs(listLayout1) {
          width = Dimension.percent(0.9f) // dimension을 지정해야 현재 레이아웃의 크기가 정상적으로 전해진다.
          // 그렇지 않으면 fillMaxSize에 의해서 어떤 배치에도 상관없이 현재 화면 크기와 똑같이 된다.
          centerTo(parent)
        },
    )
    FloatingActionButton(onClick = viewModel::showAddListDataView) {

    }
  }
}

@Composable
fun ListLayout(modifier: Modifier = Modifier) {
  val viewModel = viewModel<ListViewModel>()
  Column(modifier) {
    ListHead(viewModel.isAllChecked, viewModel::onAllCheckedChanged, viewModel::removeListData)
    LazyColumn {
      items(items = viewModel.getAllListData()) { listData ->
        ListItem(
          listData,
          viewModel.isCheckedList[listData.no] ?: false,
          viewModel::onCheckedChange,
          viewModel::changeFinishState
        )
      }
    }
  }
}

@Composable
fun ListHead(
  allChecked: Boolean,
  onAllCheckedChange: () -> Unit,
  onDeleteButtonClicked: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Checkbox(checked = allChecked, onCheckedChange = { onAllCheckedChange() })
    Spacer(modifier = Modifier.weight(1f))
    Button(onClick = onDeleteButtonClicked, contentPadding = PaddingValues(0.dp)) {
      Text(text = "삭제")
    }
  }
}

// ViewModel, Repository 계속 생성됨 해결 필요 -> singleton? CompositionLocal? hilt? viewModel!
@Composable
fun ListItem(
  listData: ListData,
  isChecked: Boolean,
  onCheckedChange: (Int) -> Unit,
  onCompleteButtonClick: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  println("ListItem${listData.no} is created")
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier.fillMaxSize(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(
        checked = isChecked,
        onCheckedChange = { onCheckedChange(listData.no) },
      )
      Text(text = "${listData.no}")
    }
    Text(text = listData.todo, textDecoration = TextDecoration.LineThrough)
    Button(onClick = { onCompleteButtonClick(listData.no) }, contentPadding = PaddingValues(0.dp)) {
      Text(text = "완료")
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Preview() {
  Scaffold {
    ListView()
  }
}