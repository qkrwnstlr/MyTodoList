package com.example.mytodolist.data.repository

import androidx.compose.runtime.mutableStateMapOf
import com.example.mytodolist.model.ListData
import com.example.mytodolist.model.ListDataDTO
import com.example.mytodolist.model.ListState

class MemoryListDataRepository : ListDataRepository {
  private val _memoryRepository =
    mutableStateMapOf<Int, ListDataDTO>(
      1 to ListDataDTO(1, "1", true),
      2 to ListDataDTO(2, "2", true),
      3 to ListDataDTO(3, "3", false),
      4 to ListDataDTO(4, "4", false),
    )

  private val _listDataList = mutableStateMapOf<Int, ListData>()
  private var _currentNo = 4;

  override fun getAllListData(vararg filter: ListState): Map<Int, ListData> {
    _listDataList.clear()
    for (no in _memoryRepository.keys) {
      if (filter.contains(ListState.fromBoolean(_memoryRepository[no]!!.isFinished)))
        _listDataList[no] = ListData.fromListDataDTO(_memoryRepository[no]!!)
    }
    return _listDataList
  }

  override fun addListData(todo: String) {
    val listData = ListData(++_currentNo, todo, ListState.UNFINISHED)
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
  }
}