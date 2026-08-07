package com.weekendwarriorscompanion.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import java.io.InputStream
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCropScreen(
    imageUri: Uri,
    onImageCropped: (Bitmap) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val density = LocalDensity.current
    
    LaunchedEffect(imageUri) {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        bitmap = BitmapFactory.decodeStream(inputStream)
    }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = constraints.maxWidth.toFloat()
        val screenHeight = constraints.maxHeight.toFloat()
        val cropSizePx = with(density) { 300.dp.toPx() }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("ADJUST PORTRAIT") },
                    navigationIcon = {
                        TextButton(onClick = onCancel) { Text("BACK", color = Color.White) }
                    },
                    actions = {
                        Button(onClick = {
                            bitmap?.let { b ->
                                val cropped = cropBitmap(
                                    b, screenWidth, screenHeight, scale, offset, cropSizePx
                                )
                                onImageCropped(cropped)
                            }
                        }) {
                            Text("SAVE")
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.Black)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = max(0.5f, scale * zoom)
                            offset += pan
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                bitmap?.let { b ->
                    androidx.compose.foundation.Image(
                        bitmap = b.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offset.x,
                                translationY = offset.y
                            ),
                        contentScale = ContentScale.Fit
                    )
                }

                // Overlay circle guide
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Outer darkening
                    drawRect(Color.Black.copy(alpha = 0.5f))
                    // The guide circle
                    drawCircle(
                        color = Color.White,
                        radius = cropSizePx / 2,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                    )
                }
                
                Text(
                    "Pinch to zoom, drag to move",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private fun cropBitmap(
    source: Bitmap,
    containerWidth: Float,
    containerHeight: Float,
    userScale: Float,
    userOffset: Offset,
    cropSize: Float
): Bitmap {
    val bitmapWidth = source.width.toFloat()
    val bitmapHeight = source.height.toFloat()

    // ContentScale.Fit logic: find the scale that fits the bitmap in the container
    val fitScale = min(containerWidth / bitmapWidth, containerHeight / bitmapHeight)
    
    // The image is centered in the container before user transforms
    val initialTranslateX = (containerWidth - bitmapWidth * fitScale) / 2f
    val initialTranslateY = (containerHeight - bitmapHeight * fitScale) / 2f

    val matrix = Matrix()
    
    // Step 1: Scale to 'Fit' size
    matrix.postScale(fitScale, fitScale)
    
    // Step 2: Center it in screen
    matrix.postTranslate(initialTranslateX, initialTranslateY)
    
    // Step 3: Apply user zoom and pan relative to screen center
    // We want to scale around the center of the container
    matrix.postScale(userScale, userScale, containerWidth / 2f, containerHeight / 2f)
    matrix.postTranslate(userOffset.x, userOffset.y)
    
    // Step 4: Now move the 'crop area' (center of screen) to (0,0) of our new bitmap
    // The crop area is a square of size 'cropSize' centered at (containerWidth/2, containerHeight/2)
    val cropLeft = (containerWidth - cropSize) / 2f
    val cropTop = (containerHeight - cropSize) / 2f
    
    matrix.postTranslate(-cropLeft, -cropTop)

    val croppedBitmap = Bitmap.createBitmap(cropSize.toInt(), cropSize.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(croppedBitmap)
    canvas.drawBitmap(source, matrix, android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG))
    
    return croppedBitmap
}
