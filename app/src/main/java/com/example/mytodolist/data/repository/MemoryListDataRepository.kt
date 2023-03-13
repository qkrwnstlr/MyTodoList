package com.example.mytodolist.data.repository

import androidx.compose.runtime.mutableStateMapOf
import com.example.mytodolist.model.ListData

class MemoryListDataRepository : ListDataRepository {
  init {
    println("ListDataRepository is created")
  }

  private val _listDataList =
    mutableStateMapOf<Int, ListData>(
      1 to ListData(1, "1", false),
      2 to ListData(2, "2", false),
      3 to ListData(3, "3", false),
      4 to ListData(4, "4", false),
    )

  override fun getAllListData(): List<ListData> = _listDataList.values.toList()

  override fun addListData(listData: ListData) {
    _listDataList[listData.no] = listData
  }

  override fun removeListData(no: Int) {
    _listDataList.remove(no)
  }

  override fun getOneListDataWithNo(no: Int): ListData? = _listDataList[no]

  override fun changeFinishState(no: Int) {
    _listDataList[no]?.isFinish = !_listDataList[no]?.isFinish!!
  }
}