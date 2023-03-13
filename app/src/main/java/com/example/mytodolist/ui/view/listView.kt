package com.example.mytodolist.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytodolist.model.ListData
import com.example.mytodolist.ui.viewmodel.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(modifier: Modifier = Modifier) {
  val viewModel = viewModel<ListViewModel>()
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("App Title") },
        navigationIcon = {
          IconButton(onClick = { /* Handle navigation icon click */ }) {
            Icon(Icons.Default.ArrowBack, "Back")
          }
        },
        actions = {
          IconButton(onClick = { /* Handle action icon click */ }) {
            Icon(Icons.Default.MoreVert, "More")
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = viewModel::showAddListDataView,
        shape = RoundedCornerShape(16.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.Add,
          contentDescription = "Add FAB",
          tint = Color.White,
        )
      }
    }
  ) {
    ConstraintLayout(
      modifier
        .fillMaxSize()
        .padding(it)
    ) {
      val (listLayout1) = createRefs() // 제약에 사용할 참조 생성
      ListLayout(
        Modifier
          .constrainAs(listLayout1) {
            width = Dimension.fillToConstraints // dimension을 지정해야 현재 레이아웃의 크기가 정상적으로 전해진다.
            // 그렇지 않으면 fillMaxSize에 의해서 어떤 배치에도 상관없이 현재 화면 크기와 똑같이 된다.
            centerHorizontallyTo(parent)
            top.linkTo(parent.top)
          },
      )
    }
  }
}

@Composable
fun ListLayout(modifier: Modifier = Modifier) {
  val viewModel = viewModel<ListViewModel>()
  Column(modifier) {
    ListHead(
      viewModel::removeListData,
      viewModel.searchText,
      viewModel::onSearchTextChange,
      viewModel::onIsSearchFilterDDMExpendedChange,
      viewModel.isSearchFilterDDMExpended,
      viewModel::onIsSearchFilterDDMExpendedChange,
      viewModel.searchFilterList,
      viewModel::onSearchFilterCheckedChange,
    )
    ListTop(
      viewModel.isAllChecked,
      viewModel::onAllCheckedChanged,
    )
    LazyColumn { // 임마는 왜 자꾸 호출됨...?
      items(items = viewModel.listDataList.toList()) { (no, listData) ->
        println("(ListView) isFinished2 $no : ${listData.isFinish}")
        ListItem(
          listData,
          viewModel.isCheckedList[no] ?: false,
          viewModel::onCheckedChange,
          viewModel::changeFinishState
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListHead(
  onDeleteButtonClicked: () -> Unit,
  searchText: String,
  onSearchTextChange: (String) -> Unit,
  onSearchFilterListButtonClicked: () -> Unit,
  isSearchFilterDDMExpended: Boolean,
  onIsSearchFilterDDMExpendedChange: () -> Unit,
  searchFilterList: Map<String, Boolean>,
  onSearchFilterCheckedChange: (String, Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = modifier,
      verticalAlignment = Alignment.CenterVertically
    ) {
      BasicTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier = Modifier
          .weight(1f)
          .height(ButtonDefaults.MinHeight)
          .padding(start = 10.dp),
      ) {
        TextFieldDefaults.TextFieldDecorationBox(
          value = searchText,
          innerTextField = it,
          enabled = false,
          singleLine = true,
          visualTransformation = VisualTransformation.None,
          interactionSource = remember { MutableInteractionSource() },
          contentPadding = PaddingValues(0.dp), // 패딩 삭제
          leadingIcon = { Icon(Icons.Rounded.Search, "Search Btn") },
          trailingIcon = {
            Box {
              IconButton(onClick = onSearchFilterListButtonClicked) {
                println("(ListView) isSearchFilterDDMExpended $isSearchFilterDDMExpended")
                Icon(
                  Icons.Rounded.FilterList,
                  "Search Btn"
                )
              }
              SearchFilterList(
                expanded = isSearchFilterDDMExpended,
                onDismissRequest = onIsSearchFilterDDMExpendedChange,
                dropdownMenuItemList = searchFilterList,
                onDropdownMenuClicked = onSearchFilterCheckedChange,
              )
            }
          },
          shape = ButtonDefaults.shape,
          colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent // 밑줄 삭제
          )
        )
      }
      IconButton(
        onClick = onDeleteButtonClicked,
        modifier = Modifier
      ) {
        Icon(
          imageVector = Icons.Rounded.Delete,
          contentDescription = "Delete Btn",
          tint = Color.Gray
        )
      }
    }
  }
}

@Composable
fun ListTop(
  allChecked: Boolean,
  onAllCheckedChange: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
      Checkbox(
        checked = allChecked,
        onCheckedChange = { onAllCheckedChange() },
      )
      Text(
        text = "#",
        fontWeight = FontWeight.Bold,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center
      )
    }
    Text(
      text = "TODO",
      modifier = Modifier.weight(4f),
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold
    )
    Text(
      text = "Finish",
      modifier = Modifier
        .width(ButtonDefaults.MinWidth)
        .padding(horizontal = 10.dp)
        .weight(1f),
      textAlign = TextAlign.Center,
      fontWeight = FontWeight.Bold
    )
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
  println("(ListView) ListItem${listData.no} : ${listData.isFinish} is created")
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier.fillMaxSize(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
      Checkbox(
        checked = isChecked,
        onCheckedChange = { onCheckedChange(listData.no) },
      )
      Text(text = "${listData.no}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
    }
    Text(
      text = listData.todo,
      /*
      FIXME : TextDecoration이 처음에 None이 아닌 다른 것으로 설정된 후에는 정상적으로 바뀌지 않는 버그
      None에서 시작하면 다른걸로 바껴도 정상적으로 작동하나, None이 아닌 다른 것으로 시작하면 안 바뀜
      */
      textDecoration = if (listData.isFinish) TextDecoration.LineThrough else TextDecoration.None,
      modifier = Modifier.weight(4f),
      textAlign = TextAlign.Center
    )
    Button(
      onClick = { onCompleteButtonClick(listData.no) },
      contentPadding = PaddingValues(0.dp),
      modifier = Modifier
        .padding(horizontal = 10.dp)
        .weight(1f)
    ) {
      Text(text = if (listData.isFinish) "취소" else "완료")
    }
  }
}

@Composable
fun SearchFilterList(
  expanded: Boolean,
  onDismissRequest: () -> Unit,
  dropdownMenuItemList: Map<String, Boolean>,
  onDropdownMenuClicked: (String, Boolean) -> Unit
) {
  DropdownMenu(
    expanded = expanded,
    onDismissRequest = onDismissRequest,
    offset = DpOffset(0.dp, 10.dp),
    modifier = Modifier.clip(ButtonDefaults.shape)
  ) {
    dropdownMenuItemList.forEach { (filterItem, isChecked) ->
      DropdownMenuItem(
        text = {
          println("(SearchFilterList) expended $expanded")
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = filterItem, Modifier.weight(1f))
            Icon(
              imageVector = Icons.Rounded.Check,
              contentDescription = "$filterItem is checked Icon",
              tint = if (isChecked) Color.Black else Color.Transparent,
              modifier = Modifier.padding(start = 10.dp)
            )
          }
        },
        onClick = { onDropdownMenuClicked(filterItem, !isChecked) },
        modifier = Modifier.clip(ButtonDefaults.shape)
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Preview() {
  Scaffold {
    ListView(Modifier.padding(it))
  }
}