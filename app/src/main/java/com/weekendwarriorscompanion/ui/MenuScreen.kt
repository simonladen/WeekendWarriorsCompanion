package com.weekendwarriorscompanion.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
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
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header inspired by the book cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "WEEKEND",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "WARRIORS",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Muted blue-grey divider
            Spacer(modifier = Modifier.height(4.dp).fillMaxWidth().background(MaterialTheme.colorScheme.secondary))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val buttonModifier = Modifier.fillMaxWidth().height(60.dp)
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
                Button(onClick = onPlay, modifier = Modifier.fillMaxWidth().height(70.dp), shape = buttonShape, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text("PLAY", fontSize = 24.sp, fontWeight = FontWeight.Black)
                }

                Spacer(modifier = Modifier.weight(1f))

                // Footer
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "THE ULTIMATE TABLETOP COMPANION",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
