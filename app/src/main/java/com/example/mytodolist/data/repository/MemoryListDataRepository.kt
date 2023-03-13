package com.example.mytodolist.data.repository

import androidx.compose.runtime.mutableStateMapOf
import com.example.mytodolist.model.ListData

class MemoryListDataRepository : ListDataRepository {
  init {
    println("ListDataRepository is created")
  }

  private val _listDataList =
    mutableStateMapOf<Int, ListData>(
      1 to ListData(1, "1", true),
      2 to ListData(2, "2", true),
      3 to ListData(3, "3", false),
      4 to ListData(4, "4", false),
    )

  override fun getAllListData(): Map<Int, ListData> = _listDataList

  override fun addListData(listData: ListData) {
    _listDataList[listData.no] = listData
  }

  override fun removeListData(no: Int) {
    _listDataList.remove(no)
  }

  override fun getOneListDataWithNo(no: Int): ListData? = _listDataList[no]

  override fun changeFinishState(no: Int) {
    // listDataList가 state이므로 이가 참조하는 객체 자체가 변경되어야 상태가 변경됨
    _listDataList[no] = ListData(no, _listDataList[no]!!.todo, !_listDataList[no]!!.isFinish)
    println("(MemoryListDataRepository) isFinished($no) : ${_listDataList[no]?.isFinish}")
  }
}