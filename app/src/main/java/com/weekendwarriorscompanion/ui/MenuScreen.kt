package com.weekendwarriorscompanion.ui

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(
    onCreateCharacter: () -> Unit,
    onEditCharacter: () -> Unit,
    onRemoveCharacter: () -> Unit,
    onPlay: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header inspired by the book cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = if (isLandscape) 120.dp else 200.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val titleFontSize = if (isLandscape) 40.sp else 60.sp
                    Text(
                        text = "WEEKEND",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = titleFontSize,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "WARRIORS",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = titleFontSize,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "THE ULTIMATE TABLETOP COMPANION",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Muted blue-grey divider
            Spacer(modifier = Modifier.height(4.dp).fillMaxWidth().background(MaterialTheme.colorScheme.secondary))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val buttonModifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                val buttonShape = RoundedCornerShape(8.dp)
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                )

                Button(onClick = onCreateCharacter, modifier = buttonModifier, shape = buttonShape, colors = buttonColors) {
                    Text("CREATE CHARACTER", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onEditCharacter, modifier = buttonModifier, shape = buttonShape, colors = buttonColors) {
                    Text("EDIT CHARACTER", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onRemoveCharacter, modifier = buttonModifier, shape = buttonShape, colors = buttonColors) {
                    Text("REMOVE CHARACTER", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onPlay,
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = buttonShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("PLAY", fontSize = 24.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}
