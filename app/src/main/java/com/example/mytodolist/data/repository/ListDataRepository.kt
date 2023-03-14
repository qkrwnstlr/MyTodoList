package com.example.mytodolist.data.repository

import com.example.mytodolist.model.ListData
import com.example.mytodolist.model.ListFilter

interface ListDataRepository {
  fun getAllListData(vararg filter: ListFilter): Map<Int, ListData>
  fun addListData(listData: ListData)
  fun removeListData(no: Int)
  fun getOneListDataWithNo(no: Int): ListData?
  fun changeFinishState(no: Int)
}