package com.example.mytodolist.util.checkbox

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.example.mytodolist.model.ListData

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