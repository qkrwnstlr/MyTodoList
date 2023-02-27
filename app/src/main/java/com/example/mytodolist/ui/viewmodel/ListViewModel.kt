package com.example.mytodolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mytodolist.data.repository.ListDataRepository
import com.example.mytodolist.data.repository.MemoryListDataRepository
import com.example.mytodolist.model.ListData

class ListViewModel : ViewModel() {
  init {
    println("ListViewModel is created")
  }
  private val _repository: ListDataRepository = MemoryListDataRepository()
  fun getAllListData(): List<ListData> = _repository.getAllListData()

  fun addListData(listData: ListData) {
    _repository.addListData(listData)
  }

  fun removeListData(no: Int) {
    _repository.removeListData(no)
  }

  fun changeFinishState(no: Int) {
    _repository.changeFinishState(no)
  }
}