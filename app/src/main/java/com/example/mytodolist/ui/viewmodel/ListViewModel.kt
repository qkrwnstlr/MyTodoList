package com.example.mytodolist.ui.viewmodel

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import com.example.mytodolist.data.repository.ListDataRepository
import com.example.mytodolist.data.repository.MemoryListDataRepository
import com.example.mytodolist.model.ListData
import com.example.mytodolist.model.ListState
import com.example.mytodolist.util.checkbox.CheckBoxListController
import com.example.mytodolist.util.textfield.TextFieldController

class ListViewModel : ViewModel() {
  private val _repository: ListDataRepository = MemoryListDataRepository()

  private val _searchFilterList = mutableStateMapOf<ListState, Boolean>(
    ListState.FINISHED to true,
    ListState.UNFINISHED to true,
  )

  val listDataList: Map<Int, ListData> =
    _repository.getAllListData("", *_searchFilterList.keys.filter { _searchFilterList[it]!! }
      .toTypedArray())

  private val _isCheckedList = mutableStateMapOf<Int, Boolean>()
  val checkBoxListController by lazy { CheckBoxListController(listDataList, _isCheckedList) }
  val isCheckedList: Map<Int, Boolean> = _isCheckedList

  private fun updateListData() {
    _repository.getAllListData(
      searchTextFieldController.text,
      *_searchFilterList.keys.filter { _searchFilterList[it]!! }.toTypedArray()
    )
    checkBoxListController.onAllCheckedChanged(false)
  }

  val searchTextFieldController = TextFieldController(::updateListData)
  val addTextFieldController = TextFieldController(::updateListData)

  var isAddListDataPopupExpended by mutableStateOf(false)
  fun onIsAddListDataPopupExpendedChanged() {
    isAddListDataPopupExpended = !isAddListDataPopupExpended
    addTextFieldController.clearText()
  }

  fun onAddListDataButtonClicked(todo: String) {
    _repository.addListData(todo)
    onIsAddListDataPopupExpendedChanged()
    updateListData()
  }

  fun onDeleteButtonClicked() {
    _isCheckedList.forEach { (no, isChecked) ->
      if (isChecked) _repository.removeListData(no)
    }
    updateListData()
  }

  fun onCompleteButtonClick(no: Int) {
    _repository.changeFinishState(no)
  }

  val searchFilterList: Map<ListState, Boolean> = _searchFilterList
  fun onSearchFilterCheckedChange(filterItem: ListState, changeTo: Boolean) {
    _searchFilterList[filterItem] = changeTo
  }

  var isSearchFilterDDMExpended by mutableStateOf(false)
  fun onIsSearchFilterDDMExpendedChange() {
    isSearchFilterDDMExpended = !isSearchFilterDDMExpended
    if (!isSearchFilterDDMExpended) {
      updateListData()
    }
  }
}