package com.example.disputer.core

sealed class Result {
    data object Success : Result()
    data class Error(val message: String) : Result()
}