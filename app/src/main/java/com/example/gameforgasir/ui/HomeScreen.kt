package com.example.gameforgasir.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gameforgasir.R
import com.example.gameforgasir.data.local.GameType
import com.example.gameforgasir.data.local.LocalGameForGasIrData
import com.example.gameforgasir.ui.navigation.NavigationDestination
import com.example.gameforgasir.ui.theme.GameForGASIRTheme


object HomeDestination : NavigationDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    gameTypes: List<GameType>,
    onSelectGame: (NavigationDestination) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(gameTypes) { gameType ->
            GameTypeCard(
                gameType,
                onClick = { onSelectGame(gameType.destinationScreen) }
            )
        }
    }
}

@Composable
fun GameTypeCard(
    gameType: GameType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(topStart = 12.dp, bottomEnd = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(gameType.title),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(gameType.icon),
                contentDescription = stringResource(R.string.us_map),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimensionResource(R.dimen.game_type_icon_size))
            )
        }
    }
}

@Preview
@Composable
fun GameTypeCardPreview() {
    GameForGASIRTheme {
        val sampleGameType = GameType(
            title = R.string.identify_abbreviation_of_state,
            icon = R.drawable.us_map_svgrepo_com,
            destinationScreen = HomeDestination
        )

        GameTypeCard(
            gameType = sampleGameType,
            onClick = { }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    GameForGASIRTheme {
        Surface {
            HomeScreen(
                gameTypes = LocalGameForGasIrData.gameTypes,
                onSelectGame = { }
            )
        }
    }
}