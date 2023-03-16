package com.example.mytodolist.ui.viewmodel

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import com.example.mytodolist.data.repository.ListDataRepository
import com.example.mytodolist.data.repository.MemoryListDataRepository
import com.example.mytodolist.model.ListData
import com.example.mytodolist.model.ListState

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
      searchText,
      *_searchFilterList.keys.filter { _searchFilterList[it]!! }.toTypedArray()
    )
    checkBoxListController.onAllCheckedChanged(false)
  }

  var searchText by mutableStateOf("")
  fun onSearchTextChange(value: String) {
    searchText = value
    updateListData()
  }

  var addText by mutableStateOf("")
  fun onAddTextChange(value: String) {
    addText = value
  }

  var isAddListDataPopupExpended by mutableStateOf(false)
  fun onIsAddListDataPopupExpendedChanged() {
    isAddListDataPopupExpended = !isAddListDataPopupExpended
    addText = ""
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

class CheckBoxListController(
  private val listDataList: Map<Int, ListData>,
  private val _isCheckedList: SnapshotStateMap<Int, Boolean> = mutableStateMapOf()
) {
  private var _isCheckedCount by mutableStateOf(0)
  var isAllChecked by mutableStateOf(false)

  fun onCheckedChange(no: Int, changeTo: Boolean? = null) {
    _isCheckedList[no] = changeTo ?: !(_isCheckedList[no] ?: false)
    if (_isCheckedList[no]!!) ++_isCheckedCount
    else --_isCheckedCount
    isAllChecked = _isCheckedCount == listDataList.size
  }

  fun onAllCheckedChanged(changeTo: Boolean = !isAllChecked) {
    if (changeTo) setAllCheckedList()
    else clearCheckedList()
  }

  private fun clearCheckedList() {
    isAllChecked = false
    _isCheckedList.clear()
    _isCheckedCount = 0
  }

  private fun setAllCheckedList() {
    isAllChecked = true
    listDataList.forEach { (no, _) -> _isCheckedList[no] = true }
    _isCheckedCount = listDataList.size
  }
}