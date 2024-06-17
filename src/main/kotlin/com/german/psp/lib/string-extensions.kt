package com.german.psp.lib

val String.sumOfDigits: Int
    get() = map { it.digitToInt() }.sum()