package com.example.josue.pokeapp.core

object Extensions {
    fun String?.setNameReplace(): String {
        return this?.replace('-', ' ') ?: ""
    }
}
