package com.example.pickup_pic.data.network.status_tracker

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus() {
    }
}