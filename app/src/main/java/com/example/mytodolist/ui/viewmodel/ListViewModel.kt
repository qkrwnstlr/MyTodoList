package com.example.mytodolist.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.mytodolist.data.repository.ListDataRepository
import com.example.mytodolist.data.repository.MemoryListDataRepository
import com.example.mytodolist.model.ListData
import com.example.mytodolist.model.ListFilter

class ListViewModel : ViewModel() {
  private val _repository: ListDataRepository = MemoryListDataRepository()

  private val _searchFilterList = mutableStateMapOf<ListFilter, Boolean>(
    ListFilter.FINISHED to true,
    ListFilter.UNFINISHED to true,
  )

  val listDataList: Map<Int, ListData> =
    _repository.getAllListData(*_searchFilterList.keys.filter { _searchFilterList[it]!! }
      .toTypedArray())

  private val _isCheckedList = mutableStateMapOf<Int, Boolean>()
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
    isAllChecked = _isCheckedCount == listDataList.size
  }

  fun onAllCheckedChanged() {
    if (isAllChecked) _isCheckedList.clear()
    else listDataList
      .forEach { (no, _) -> _isCheckedList[no] = true }
    _isCheckedCount = if (isAllChecked) 0 else listDataList.size
    isAllChecked = !isAllChecked
  }

  fun addListData(listData: ListData) {
    _repository.addListData(listData)
  }

  fun showAddListDataView() {

  }

  fun removeListData() {
    _isCheckedList.forEach { (no, isChecked) ->
      if (isChecked) _repository.removeListData(no)
    }
  }

  fun changeFinishState(no: Int) {
    _repository.changeFinishState(no)
  }

  val searchFilterList: Map<ListFilter, Boolean> = _searchFilterList
  fun onSearchFilterCheckedChange(filterItem: ListFilter, changeTo: Boolean) {
    _searchFilterList[filterItem] = changeTo
  }

  var isSearchFilterDDMExpended by mutableStateOf(false)
  fun onIsSearchFilterDDMExpendedChange() {
    isSearchFilterDDMExpended = !isSearchFilterDDMExpended
    if (!isSearchFilterDDMExpended){
      _repository.getAllListData(*_searchFilterList.keys.filter { _searchFilterList[it]!! }
        .toTypedArray())
      isAllChecked = false
      _isCheckedList.clear()
      _isCheckedCount = 0
    }
  }
}