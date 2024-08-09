package com.example.gameforgasir.data

import android.content.Context
import android.media.MediaPlayer
import com.example.gameforgasir.R

interface SoundEffectsRepository {
    fun playCorrectSoundEffect()
    fun playIncorrectSoundEffect()
    fun playTimesUpSoundEffect()
}

class DefaultSoundEffectsRepository(private val context: Context): SoundEffectsRepository {
    override fun playCorrectSoundEffect() {
        MediaPlayer.create(context, R.raw.correct_sound_effect).start()
    }

    override fun playIncorrectSoundEffect() {
        MediaPlayer.create(context, R.raw.error_sound_effect).start()
    }

    override fun playTimesUpSoundEffect() {
        MediaPlayer.create(context, R.raw.time_out_sound_effect).start()
    }
}