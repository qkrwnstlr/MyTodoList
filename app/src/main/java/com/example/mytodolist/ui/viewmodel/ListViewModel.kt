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

  private val _isCheckedList by lazy { mutableStateMapOf<Int, Boolean>() }

  //  private val _isCheckedList by lazy { MutableStateFlow(mutableMapOf<Int, Boolean>()) }
  private var _isCheckedCount by mutableStateOf(0)
  var isAllChecked by mutableStateOf(false)
  val isCheckedList: Map<Int, Boolean> = _isCheckedList

  fun onCheckedChange(no: Int, changeTo: Boolean? = null) {
    _isCheckedList[no] = changeTo ?: !(_isCheckedList[no] ?: false)
    if (_isCheckedList[no]!!) ++_isCheckedCount
    else --_isCheckedCount
    isAllChecked = _isCheckedCount == _repository.getAllListData().size
    println("(ListViewModel) ${_isCheckedList[no]} : $_isCheckedCount")
  }

  fun onAllCheckedChanged() {
    if (isAllChecked) _isCheckedList.clear()
    else _repository.getAllListData()
      .forEach { (no, _) -> _isCheckedList[no] = true }
    _isCheckedCount = if (isAllChecked) 0 else _repository.getAllListData().size
    isAllChecked = !isAllChecked
    println("(ListViewModel) $isAllChecked : $_isCheckedCount")
  }

  fun getAllListData(): List<ListData> = _repository.getAllListData()

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
  }

  fun showAddListDataView() {

  }
}