package com.example.mytodolist.model

data class ListData(val no: Int, var todo: String, var isFinished: ListState) {
  companion object {
    fun fromListDataDTO(listDataDTO: ListDataDTO): ListData =
      ListData(listDataDTO.no, listDataDTO.todo, ListState.fromBoolean(listDataDTO.isFinished))
  }
  fun toListDataDTO(): ListDataDTO = ListDataDTO(no, todo, isFinished.toBoolean())
}