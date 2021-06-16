package com.example.cryptotracker.models

data class Urls(
    val announcement: List<String>,
    val chat: List<String>,
    val explorer: List<String>,
    val message_board: List<String>,
    val reddit: List<String>,
    val source_code: List<String>,
    val technical_doc: List<String>,
    val twitter: List<String>,
    val website: List<String>
)