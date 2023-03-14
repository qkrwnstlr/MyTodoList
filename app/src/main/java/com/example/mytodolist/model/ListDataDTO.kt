package com.example.mytodolist.model

import org.json.JSONObject

data class ListDataDTO(val no: Int, val todo: String, val isFinished: Boolean) {
  companion object {
    fun fromJson(json: JSONObject): ListDataDTO =
      ListDataDTO(json.getInt("no"), json.getString("todo"), json.getBoolean("isFinished"))
  }

  fun toJson(): JSONObject =
    JSONObject().put("no", no).put("todo", todo).put("isFinished", isFinished)
}