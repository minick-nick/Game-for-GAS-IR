package com.example.gameforgasir.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gameforgasir.GameOfGasIrApplication
import com.example.gameforgasir.data.local.LocalGameForGasIrData
import com.example.gameforgasir.ui.IdentifyAbbreviationOfState.IdentifyAbbreviationOfStateViewModel
import com.example.gameforgasir.ui.IdentifyLicensePlateState.IdentifyLicensePlateStateViewModel
import com.example.gameforgasir.ui.IdentifyStateNickname.IdentifyStateNicknameViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            IdentifyAbbreviationOfStateViewModel(
                LocalGameForGasIrData.usStates,
                gameOfGasIrApplication().container.soundEffectsRepository
            )
        }

        initializer {
            IdentifyStateNicknameViewModel(
                LocalGameForGasIrData.usStates,
                gameOfGasIrApplication().container.soundEffectsRepository
            )
        }

        initializer {
            IdentifyLicensePlateStateViewModel(
                LocalGameForGasIrData.usStates,
                gameOfGasIrApplication().container.soundEffectsRepository
            )
        }
    }
}


fun CreationExtras.gameOfGasIrApplication(): GameOfGasIrApplication {
    return (this[
        ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY
    ] as GameOfGasIrApplication)
}