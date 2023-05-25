package com.example.videogameapp.domain.entity.storeentity

data class StoreDetailEntity (
    val desc: String,
) {
    companion object {
        fun getNullableString(string: String): String {
            return string.ifBlank { "-" }
        }
    }
}