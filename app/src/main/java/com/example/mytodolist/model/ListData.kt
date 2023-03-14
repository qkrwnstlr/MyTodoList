package com.example.mytodolist.model

data class ListData(val no: Int, var todo: String, var isFinished: ListFilter) {
  companion object {
    fun fromListDataDTO(listDataDTO: ListDataDTO): ListData =
      ListData(listDataDTO.no, listDataDTO.todo, ListFilter.fromBoolean(listDataDTO.isFinished))
  }
  fun toListDataDTO(): ListDataDTO = ListDataDTO(no, todo, isFinished.toBoolean())
}