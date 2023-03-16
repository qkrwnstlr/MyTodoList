package com.example.mytodolist.util.kmp

private fun String.kmp(pattern: String, pi: List<Int> = pattern.getPi()): Boolean {
  var j = 0
  if(pattern == "") return true
  for (i in this.indices) {
    while (j > 0 && this[i] != pattern[j]) j = pi[j - 1]
    if (this[i] == pattern[j]) {
      if (j == pattern.length - 1) return true
      else j++
    }
  }
  return false
}

private fun String.getPi(): List<Int> {
  val pi = List(this.length) { 0 }.toMutableList()
  var j = 0
  for (i in 1 until this.length) {
    while (j > 0 && this[i] != this[j]) j = pi[j - 1]
    if (this[i] == this[j]) pi[i] = ++j
  }
  return pi
}

class StringKMP(private val pattern: String) {
  private val pi by lazy { pattern.getPi() }
  fun kmp(parent: String) = parent.kmp(pattern, pi)
}