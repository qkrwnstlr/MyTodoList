package com.example.mytodolist.util.textfield

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TextFieldController(private val onTextChangeCallback: () -> Unit = {}) {
  private var _text by mutableStateOf("")
  val text = _text
  fun onTextChange(value: String, callback: () -> Unit = onTextChangeCallback) {
    _text = value
    callback()
  }
  fun clearText() {
    _text = ""
  }
}