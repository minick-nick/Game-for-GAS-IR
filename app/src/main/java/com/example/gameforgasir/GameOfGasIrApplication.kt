package com.example.gameforgasir

import android.app.Application
import com.example.gameforgasir.data.DefaultAppContainer

class GameOfGasIrApplication : Application() {
    lateinit var container: DefaultAppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}