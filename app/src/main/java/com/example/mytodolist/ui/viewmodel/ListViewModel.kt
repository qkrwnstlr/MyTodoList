package com.example.mytodolist.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.mytodolist.data.repository.ListDataRepository
import com.example.mytodolist.data.repository.MemoryListDataRepository
import com.example.mytodolist.model.ListData

class ListViewModel : ViewModel() {
  init {
    println("(ListViewModel) is created")
  }

  private val _repository: ListDataRepository = MemoryListDataRepository()

  val listDataList: Map<Int, ListData> = _repository.getAllListData()

  private val _isCheckedList by lazy { mutableStateMapOf<Int, Boolean>() }
  private var _isCheckedCount by mutableStateOf(0)
  var isAllChecked by mutableStateOf(false)
  val isCheckedList: Map<Int, Boolean> = _isCheckedList

  var searchText by mutableStateOf("")
  fun onSearchTextChange(value: String) {
    searchText = value
  }

  fun onCheckedChange(no: Int, changeTo: Boolean? = null) {
    _isCheckedList[no] = changeTo ?: !(_isCheckedList[no] ?: false)
    if (_isCheckedList[no]!!) ++_isCheckedCount
    else --_isCheckedCount
    isAllChecked = _isCheckedCount == _repository.getAllListData().size
  }

  fun onAllCheckedChanged() {
    if (isAllChecked) _isCheckedList.clear()
    else _repository.getAllListData()
      .forEach { (no, _) -> _isCheckedList[no] = true }
    _isCheckedCount = if (isAllChecked) 0 else _repository.getAllListData().size
    isAllChecked = !isAllChecked
  }

  fun addListData(listData: ListData) {
    _repository.addListData(listData)
  }

  fun removeListData() {
    _isCheckedList.forEach { (no, isChecked) ->
      if (isChecked) _repository.removeListData(no)
    }
  }

  fun changeFinishState(no: Int) {
    _repository.changeFinishState(no)
    println("(ListViewModel) isFinished($no) : ${listDataList[no]!!.isFinish}")
  }

  fun showAddListDataView() {

  }

  private val _searchFilterList = mutableStateMapOf<String, Boolean>(
    "Finished" to true,
    "unFinished" to true,
  )
  val searchFilterList: Map<String, Boolean> = _searchFilterList
  fun onSearchFilterCheckedChange(filterItem: String, changeTo: Boolean) {
    _searchFilterList[filterItem] = changeTo
  }

  var isSearchFilterDDMExpended by mutableStateOf(false)
  fun onIsSearchFilterDDMExpendedChange() {
    isSearchFilterDDMExpended = !isSearchFilterDDMExpended
    println("(ViewModel)isSearchFilterDDMExpended $isSearchFilterDDMExpended")
  }
}