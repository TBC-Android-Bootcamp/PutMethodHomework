package com.example.puthomework

interface CustomCallback {
    fun onSuccess(body: String) {}
    fun onFailed(error: String) {}
}