package com.example.gameforgasir.data

import android.content.Context

interface AppContainer {
    val soundEffectsRepository: SoundEffectsRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    override val soundEffectsRepository: SoundEffectsRepository by lazy {
        SoundEffectsRepository(context)
    }
}