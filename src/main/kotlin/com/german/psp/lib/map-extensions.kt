package com.german.psp.lib

fun <K, V> MutableMap<K, MutableList<V>>.appendValue(key: K, value: V) {
    this[key]?.add(value) ?: this.put(key, mutableListOf(value))
}