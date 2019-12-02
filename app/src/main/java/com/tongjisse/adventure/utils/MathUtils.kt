package com.tongjisse.adventure.utils

import java.util.*

class MathUtils {
    companion object {
        fun getRandom(min: Int, max: Int): String {
            val random = Random()
            val s = random.nextInt(max) % (max - min + 1) + min
            return s.toString()
        }
    }
}