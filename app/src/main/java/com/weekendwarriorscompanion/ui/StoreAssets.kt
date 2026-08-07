package com.weekendwarriorscompanion.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weekendwarriorscompanion.ui.theme.WeekendWarriorsCompanionTheme
import com.weekendwarriorscompanion.ui.theme.WarriorOrange
import com.weekendwarriorscompanion.ui.theme.WarriorBlue
import com.weekendwarriorscompanion.ui.theme.WarriorLightBlue

@Composable
fun PlayStoreFeatureGraphic() {
    WeekendWarriorsCompanionTheme {
        Box(
            modifier = Modifier
                .size(width = 1024.dp, height = 500.dp)
                .background(WarriorOrange),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "WEEKEND",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 120.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp
                    ),
                    color = Color.White
                )
                Text(
                    text = "WARRIORS",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 120.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp
                    ),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = WarriorBlue,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "THE ULTIMATE TABLETOP COMPANION",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PlayStoreIcon() {
    WeekendWarriorsCompanionTheme {
        Box(
            modifier = Modifier
                .size(512.dp)
                .background(WarriorOrange),
            contentAlignment = Alignment.Center
        ) {
            // Recreating the logo from ic_launcher_foreground roughly
            Box(modifier = Modifier.size(400.dp)) {
                // We can't easily use the vector directly in a simple way without access to resources here
                // but we can draw it with Canvas to ensure it looks right in the preview
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    val wColor = Color.White
                    val strokeColor = Color(0xFF3E5664)
                    val strokeWidth = 15.dp.toPx()

                    fun drawW(offset: androidx.compose.ui.geometry.Offset, scale: Float) {
                        val path = androidx.compose.ui.graphics.Path().apply {
                            moveTo(offset.x + 0 * scale, offset.y + 0 * scale)
                            lineTo(offset.x + 12.dp.toPx() * scale, offset.y + 40.dp.toPx() * scale)
                            lineTo(offset.x + 20.dp.toPx() * scale, offset.y + 20.dp.toPx() * scale)
                            lineTo(offset.x + 28.dp.toPx() * scale, offset.y + 40.dp.toPx() * scale)
                            lineTo(offset.x + 40.dp.toPx() * scale, offset.y + 0 * scale)
                            lineTo(offset.x + 30.dp.toPx() * scale, offset.y + 0 * scale)
                            lineTo(offset.x + 24.dp.toPx() * scale, offset.y + 24.dp.toPx() * scale)
                            lineTo(offset.x + 20.dp.toPx() * scale, offset.y + 12.dp.toPx() * scale)
                            lineTo(offset.x + 16.dp.toPx() * scale, offset.y + 24.dp.toPx() * scale)
                            lineTo(offset.x + 10.dp.toPx() * scale, offset.y + 0 * scale)
                            close()
                        }
                        drawPath(path, color = wColor)
                        drawPath(path, color = strokeColor, style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth, join = androidx.compose.ui.graphics.StrokeJoin.Round))
                    }

                    drawW(androidx.compose.ui.geometry.Offset(50.dp.toPx(), 50.dp.toPx()), 4f)
                    drawW(androidx.compose.ui.geometry.Offset(200.dp.toPx(), 200.dp.toPx()), 4f)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 512, heightDp = 512)
@Composable
fun PreviewAppIcon() {
    PlayStoreIcon()
}

@Preview(showBackground = true, widthDp = 1024, heightDp = 500)
@Composable
fun PreviewFeatureGraphic() {
    PlayStoreFeatureGraphic()
}
