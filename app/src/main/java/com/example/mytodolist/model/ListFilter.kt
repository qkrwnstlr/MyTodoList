package com.example.mytodolist.model

enum class ListFilter {
  FINISHED {
    override fun toBoolean(): Boolean = true
    override fun toString(): String = "FINISHED"
  },
  UNFINISHED {
    override fun toBoolean(): Boolean = false
    override fun toString(): String = "UNFINISHED"
  };

  companion object {
    fun fromBoolean(data: Boolean): ListFilter {
      return if (data) FINISHED else UNFINISHED
    }

    fun fromString(data: String): ListFilter {
      return when (data) {
        "FINISH" -> FINISHED
        else -> UNFINISHED
      }
    }
  }

  abstract fun toBoolean(): Boolean
  abstract override fun toString(): String
}