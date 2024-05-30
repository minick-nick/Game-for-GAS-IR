package com.example.gameforgasir.data

import android.content.Context
import android.media.MediaPlayer
import com.example.gameforgasir.R

class SoundEffectsRepository(private val context: Context) {
    fun playCorrectSoundEffect() {
        MediaPlayer.create(context, R.raw.correct_sound_effect).start()
    }

    fun playIncorrectSoundEffect() {
        MediaPlayer.create(context, R.raw.error_sound_effect).start()
    }

    fun playTimesUpSoundEffect() {
        MediaPlayer.create(context, R.raw.time_out_sound_effect).start()
    }
}