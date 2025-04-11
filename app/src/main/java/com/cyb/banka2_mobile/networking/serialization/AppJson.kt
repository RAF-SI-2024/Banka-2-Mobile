package com.cyb.banka2_mobile.networking.serialization

import kotlinx.serialization.json.Json

val AppJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}