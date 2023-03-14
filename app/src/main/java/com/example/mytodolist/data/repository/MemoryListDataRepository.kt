package com.example.mytodolist.data.repository

import androidx.compose.runtime.mutableStateMapOf
import com.example.mytodolist.model.ListData
import com.example.mytodolist.model.ListDataDTO
import com.example.mytodolist.model.ListFilter

class MemoryListDataRepository : ListDataRepository {
  init {
    println("ListDataRepository is created")
  }

  private val _memoryRepository =
    mutableStateMapOf<Int, ListDataDTO>(
      1 to ListDataDTO(1, "1", true),
      2 to ListDataDTO(2, "2", true),
      3 to ListDataDTO(3, "3", false),
      4 to ListDataDTO(4, "4", false),
    )

  private val _listDataList = mutableStateMapOf<Int, ListData>()

  override fun getAllListData(vararg filter: ListFilter): Map<Int, ListData> {
    println("(MemoryListDataRepository) ListDataList1 : $_listDataList, ${_listDataList.size}")
    _listDataList.clear()
    println("(MemoryListDataRepository) filter : $filter, ${filter.size}")
    for (no in _memoryRepository.keys) {
      if (filter.contains(ListFilter.fromBoolean(_memoryRepository[no]!!.isFinished)))
        _listDataList[no] = ListData.fromListDataDTO(_memoryRepository[no]!!)
    }
    println("(MemoryListDataRepository) ListDataList2 : $_listDataList, ${_listDataList.size}")
    return _listDataList
  }

  override fun addListData(listData: ListData) {
    _memoryRepository[listData.no] = listData.toListDataDTO()
  }

  override fun removeListData(no: Int) {
    _memoryRepository.remove(no)
  }

  override fun getOneListDataWithNo(no: Int): ListData =
    ListData.fromListDataDTO(_memoryRepository[no]!!)

  override fun changeFinishState(no: Int) {
    // listDataList가 state이므로 이가 참조하는 객체 자체가 변경되어야 상태가 변경됨
    _memoryRepository[no] =
      ListDataDTO(no, _memoryRepository[no]!!.todo, !_memoryRepository[no]!!.isFinished)
    _listDataList[no] = ListData.fromListDataDTO(_memoryRepository[no]!!)
    println("(MemoryListDataRepository) isFinished($no) : ${_memoryRepository[no]?.isFinished}")
  }
}