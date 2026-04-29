package com.example.budgetapplication.screens.main_screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetapplication.views.OcrViewModel
import android.util.Base64
import java.io.File
import java.nio.charset.StandardCharsets

/**
 * Modern OCR Camera Screen
 * Features:
 * - Live CameraX preview filling the screen
 * - Bottom controls: Gallery (left), Capture (center), Manual Input (right)
 * - Immediate processing via OcrViewModel after capture/selection
 * - Auto-navigation to OCRReview on success
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OCR(
    onNavigateToReview: (String) -> Unit,
    onNavigateToManual: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val ocrViewModel: OcrViewModel = viewModel()

    var lastPreviewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val imageCaptureState = remember { mutableStateOf<ImageCapture?>(null) }
    var isProcessing by remember { mutableStateOf(false) }
    var processingError by remember { mutableStateOf<String?>(null) }

    // Gallery: select multiple images and process immediately
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        if (!uris.isNullOrEmpty()) {
            isProcessing = true
            processingError = null
            val files = uris.map { ocrViewModel.uriToFile(it) }
            lastPreviewBitmap = loadBitmapFromUri(context, uris.first())
            ocrViewModel.processFiles(files) { mergedJson, error ->
                isProcessing = false
                if (mergedJson != null) {
                    val encoded = Base64.encodeToString(mergedJson.toByteArray(StandardCharsets.UTF_8), Base64.DEFAULT)
                    onNavigateToReview(encoded)
                } else {
                    processingError = error ?: "Failed to process images"
                }
            }
        }
    }

    // Camera permission request
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        // No auto-action on grant; user must tap capture button again
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // CameraX PreviewView - fills entire background
        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
                val imageCapture = ImageCapture.Builder().build()
                imageCaptureState.value = imageCapture

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
                } catch (_: Exception) {
                    // Best-effort; ignore binding errors
                }
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        }, modifier = Modifier.fillMaxSize())

        // Top-right: small preview of last captured/selected image
        lastPreviewBitmap?.let { bmp ->
            Card(modifier = Modifier
                .padding(16.dp)
                .size(96.dp)
                .align(Alignment.TopEnd), colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.6f))) {
                Image(bitmap = bmp.asImageBitmap(), contentDescription = "Last capture")
            }
        }

        // Loading overlay
        if (isProcessing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Processing receipt...", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        // Error message
        processingError?.let { error ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Card(modifier = Modifier
                    .padding(32.dp)
                    .background(Color.Red.copy(alpha = 0.8f))) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Text(error, color = Color.White, style = MaterialTheme.typography.bodySmall)
                        Button(onClick = { processingError = null }, modifier = Modifier.padding(top = 16.dp)) {
                            Text("Dismiss")
                        }
                    }
                }
            }
        }

        // Bottom: control buttons (Gallery | Capture | Manual)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .align(Alignment.BottomCenter), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            // Gallery button
            if (!isProcessing) {
                IconButton(onClick = { galleryLauncher.launch("image/*") }, modifier = Modifier.size(56.dp)) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery", tint = Color.White)
                }
            } else {
                Spacer(modifier = Modifier.size(56.dp))
            }

            // Capture button (large circular white FAB with black inner circle)
            if (!isProcessing) {
                FloatingActionButton(onClick = {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                        return@FloatingActionButton
                    }

                    val imageCapture = imageCaptureState.value ?: return@FloatingActionButton
                    val photoFile = File(context.cacheDir, "capture_${System.currentTimeMillis()}.jpg")

                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                    imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            isProcessing = true
                            processingError = null
                            lastPreviewBitmap = loadBitmapFromUri(context, Uri.fromFile(photoFile))
                            ocrViewModel.processFiles(listOf(photoFile)) { mergedJson, error ->
                                isProcessing = false
                                if (mergedJson != null) {
                                    val encoded = Base64.encodeToString(mergedJson.toByteArray(StandardCharsets.UTF_8), Base64.DEFAULT)
                                    onNavigateToReview(encoded)
                                } else {
                                    processingError = error ?: "Failed to process photo"
                                }
                            }
                        }

                        override fun onError(exception: ImageCaptureException) {
                            processingError = "Camera error: ${exception.message}"
                        }
                    })
                }, containerColor = Color.White, modifier = Modifier.size(88.dp), shape = CircleShape) {
                    Box(modifier = Modifier
                        .size(64.dp)
                        .background(Color.Black, shape = CircleShape))
                }
            } else {
                Box(modifier = Modifier.size(88.dp))
            }

            // Manual input button
            if (!isProcessing) {
                IconButton(onClick = { onNavigateToManual() }, modifier = Modifier.size(56.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Manual Input", tint = Color.White)
                }
            } else {
                Spacer(modifier = Modifier.size(56.dp))
            }
        }
    }
}

private fun loadBitmapFromUri(context: Context, uri: Uri) =
    context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it) }
