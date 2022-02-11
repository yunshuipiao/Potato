package com.swensun.domain

class FormatTimeUseCase {
    operator fun invoke(int: Int): String {
        return "100"
    }

    operator fun invoke(str: String): String {
        return "10"
    }
}