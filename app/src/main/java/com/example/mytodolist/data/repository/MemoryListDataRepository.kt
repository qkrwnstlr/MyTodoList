package com.example.mytodolist.data.repository

import com.example.mytodolist.model.ListData
import kotlinx.coroutines.flow.MutableStateFlow

class MemoryListDataRepository : ListDataRepository {
  init {
    println("ListDataRepository is created")
  }
  private val _listDataList = MutableStateFlow(
    mutableMapOf<Int, ListData>(
      1 to ListData(1, "1", false),
      2 to ListData(2, "2", false),
      3 to ListData(3, "3", false),
      4 to ListData(4, "4", false),
    )
  )

  override fun getAllListData(): List<ListData> = _listDataList.value.values.toList()

  override fun addListData(listData: ListData) {
    _listDataList.value[listData.no] = listData
  }

  override fun removeListData(no: Int) {
    _listDataList.value.remove(no)
  }

  override fun getOneListDataWithNo(no: Int): ListData? = _listDataList.value[no]

  override fun changeFinishState(no: Int) {
    _listDataList.value[no]?.isFinish = !_listDataList.value[no]?.isFinish!!
  }
}