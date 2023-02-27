package com.example.mytodolist.data.repository

import com.example.mytodolist.model.ListData

interface ListDataRepository {
  fun getAllListData(): List<ListData>
  fun addListData(listData: ListData)
  fun removeListData(no: Int)
  fun getOneListDataWithNo(no: Int): ListData?
  fun changeFinishState(no: Int)
}