package com.weekendwarriorscompanion.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.weekendwarriorscompanion.model.Character
import com.weekendwarriorscompanion.ui.theme.WeekendWarriorsCompanionTheme

// Mock data for previews
private val mockRoster = listOf(
    Character(name = "WARRIOR 1", role = "TANK", skill = "LEADERSHIP"),
    Character(name = "ARCHER 2", role = "DPS", skill = "PRECISION")
)

@Preview(name = "7 Inch Tablet - Menu", widthDp = 600, heightDp = 1024, showBackground = true)
@Composable
fun PreviewMenu7Inch() {
    WeekendWarriorsCompanionTheme {
        MenuScreen({}, {}, {}, {})
    }
}

@Preview(name = "10 Inch Tablet - Menu", widthDp = 800, heightDp = 1280, showBackground = true)
@Composable
fun PreviewMenu10Inch() {
    WeekendWarriorsCompanionTheme {
        MenuScreen({}, {}, {}, {})
    }
}

@Preview(name = "7 Inch Tablet - Roster", widthDp = 600, heightDp = 1024, showBackground = true)
@Composable
fun PreviewRoster7Inch() {
    WeekendWarriorsCompanionTheme {
        RosterScreen(
            roster = mockRoster, 
            title = "Your Roster", 
            multiSelect = false,
            onCharacterSelected = {},
            onCharactersAction = {},
            onBack = {}
        )
    }
}

@Preview(name = "10 Inch Tablet - Roster", widthDp = 800, heightDp = 1280, showBackground = true)
@Composable
fun PreviewRoster10Inch() {
    WeekendWarriorsCompanionTheme {
        RosterScreen(
            roster = mockRoster, 
            title = "Your Roster", 
            multiSelect = true,
            onCharacterSelected = {},
            onCharactersAction = {},
            onBack = {}
        )
    }
}
