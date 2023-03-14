package com.example.mytodolist.model

enum class ListState {
  FINISHED {
    override fun toBoolean(): Boolean = true
    override fun toString(): String = "FINISHED"
  },
  UNFINISHED {
    override fun toBoolean(): Boolean = false
    override fun toString(): String = "UNFINISHED"
  };

  companion object {
    fun fromBoolean(data: Boolean): ListState {
      return if (data) FINISHED else UNFINISHED
    }

    fun fromString(data: String): ListState {
      return when (data) {
        "FINISH" -> FINISHED
        else -> UNFINISHED
      }
    }
  }

  abstract fun toBoolean(): Boolean
  abstract override fun toString(): String
}