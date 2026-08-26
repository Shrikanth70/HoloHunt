package com.vyra.app.feature.capture

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FlashAuto
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vyra.app.R
import com.vyra.app.core.designsystem.component.VyraButton
import com.vyra.app.core.designsystem.component.VyraButtonStyle
import com.vyra.app.core.designsystem.theme.VyraActionCyan
import com.vyra.app.core.designsystem.theme.VyraGlow
import com.vyra.app.core.media.camera.CameraFlashMode
import com.vyra.app.core.media.camera.VideoRecordingState
import com.vyra.app.core.media.model.MediaItem
import com.vyra.app.core.media.model.MediaType

@Composable
fun CaptureRoute(
    onMediaCaptured: (MediaItem) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CaptureViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.capturedMedia) {
        uiState.capturedMedia?.let { media ->
            onMediaCaptured(media)
            viewModel.consumeCapturedMedia()
        }
    }

    CaptureScreen(
        uiState = uiState,
        onModeSelected = viewModel::onModeSelected,
        onToggleLens = viewModel::toggleLens,
        onToggleFlash = viewModel::toggleFlash,
        onCapturePhoto = viewModel::capturePhoto,
        onToggleVideoRecording = viewModel::toggleVideoRecording,
        onGallerySelected = viewModel::onGalleryMediaSelected,
        onPermissionsResult = viewModel::onPermissionsResult,
        onBindCamera = viewModel::bindCamera,
        onFocusOn = viewModel::focusOn,
        onClearError = viewModel::clearError,
        onClose = onClose,
        modifier = modifier,
    )
}

@Composable
fun CaptureScreen(
    uiState: CaptureUiState,
    onModeSelected: (MediaType) -> Unit,
    onToggleLens: () -> Unit,
    onToggleFlash: () -> Unit,
    onCapturePhoto: () -> Unit,
    onToggleVideoRecording: () -> Unit,
    onGallerySelected: (Uri) -> Unit,
    onPermissionsResult: (Boolean, Boolean) -> Unit,
    onBindCamera: (androidx.lifecycle.LifecycleOwner, PreviewView) -> Unit,
    onFocusOn: (Float, Float, PreviewView) -> Unit,
    onClearError: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }

    var previewViewRef by remember { mutableStateOf<PreviewView?>(null) }

    // Permissions launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] == true
        val audioGranted = permissions[Manifest.permission.RECORD_AUDIO] == true
        onPermissionsResult(cameraGranted, audioGranted)
    }

    // Photo Picker launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let { onGallerySelected(it) }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
            ),
        )
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(error)
            onClearError()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        if (uiState.hasCameraPermission) {
            // Camera Viewfinder
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        previewViewRef = this
                        onBindCamera(lifecycleOwner, this)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            previewViewRef?.let { preview ->
                                onFocusOn(offset.x, offset.y, preview)
                            }
                        }
                    },
            )

            // Top gradient overlay for control visibility
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.7f),
                                Color.Transparent,
                            ),
                        ),
                    ),
            )

            // Top Controls Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.capture_close),
                        tint = Color.White,
                    )
                }

                // Flash toggle pill
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.45f))
                        .clickable(onClick = onToggleFlash)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = when (uiState.flashMode) {
                                CameraFlashMode.OFF -> Icons.Default.FlashOff
                                CameraFlashMode.ON -> Icons.Default.FlashOn
                                CameraFlashMode.AUTO -> Icons.Default.FlashAuto
                            },
                            contentDescription = stringResource(R.string.capture_flash_toggle),
                            tint = if (uiState.flashMode == CameraFlashMode.OFF) Color.White else VyraActionCyan,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = uiState.flashMode.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (uiState.flashMode == CameraFlashMode.OFF) Color.White else VyraActionCyan,
                        )
                    }
                }

                // Quick flip camera icon
                IconButton(
                    onClick = onToggleLens,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = stringResource(R.string.capture_flip_camera),
                        tint = Color.White,
                    )
                }
            }

            // Video Recording timer chip (shown during active recording)
            if (uiState.recordingState is VideoRecordingState.Recording) {
                val durationSec = uiState.recordingState.durationSeconds
                val minutes = durationSec / 60
                val seconds = durationSec % 60
                val formattedTime = String.format("%02d:%02d", minutes, seconds)

                val infiniteTransition = rememberInfiniteTransition(label = "recordingPulse")
                val pulseAlpha by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 0.3f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600),
                        repeatMode = RepeatMode.Reverse,
                    ),
                    label = "pulse",
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 80.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.6f))
                        .border(1.dp, Color.Red.copy(alpha = 0.5f), CircleShape)
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color.Red.copy(alpha = pulseAlpha)),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = formattedTime,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                        )
                    }
                }
            }

            // Bottom gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f),
                            ),
                        ),
                    ),
            )

            // Bottom Capture Controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Mode selector: PHOTO | VIDEO
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ModeTab(
                        title = stringResource(R.string.capture_photo_tab),
                        isSelected = uiState.selectedMode == MediaType.IMAGE,
                        onClick = { onModeSelected(MediaType.IMAGE) },
                    )
                    ModeTab(
                        title = stringResource(R.string.capture_video_tab),
                        isSelected = uiState.selectedMode == MediaType.VIDEO,
                        onClick = { onModeSelected(MediaType.VIDEO) },
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Bottom row: Gallery - Shutter Trigger - Flip Lens
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Gallery picker button
                    IconButton(
                        onClick = {
                            galleryLauncher.launch(
                                PickVisualMediaRequest(
                                    when (uiState.selectedMode) {
                                        MediaType.IMAGE -> ActivityResultContracts.PickVisualMedia.ImageOnly
                                        MediaType.VIDEO -> ActivityResultContracts.PickVisualMedia.VideoOnly
                                    },
                                ),
                            )
                        },
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = stringResource(R.string.capture_gallery_button),
                            tint = Color.White,
                            modifier = Modifier.size(24.dp),
                        )
                    }

                    // Shutter Trigger Button
                    ShutterButton(
                        mode = uiState.selectedMode,
                        recordingState = uiState.recordingState,
                        isCapturing = uiState.isCapturing,
                        onClick = {
                            when (uiState.selectedMode) {
                                MediaType.IMAGE -> onCapturePhoto()
                                MediaType.VIDEO -> onToggleVideoRecording()
                            }
                        },
                    )

                    // Flip camera button
                    IconButton(
                        onClick = onToggleLens,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cameraswitch,
                            contentDescription = stringResource(R.string.capture_flip_camera),
                            tint = Color.White,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            }
        } else {
            // Permission Required Fallback
            PermissionFallbackCard(
                onGrantClick = {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                        ),
                    )
                },
                onSettingsClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                },
                onClose = onClose,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        // Loading overlay during processing
        if (uiState.isCapturing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = VyraActionCyan,
                    modifier = Modifier.size(48.dp),
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp),
        )
    }
}

@Composable
private fun ModeTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (isSelected) VyraActionCyan else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = if (isSelected) Color.Black else Color.White.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun ShutterButton(
    mode: MediaType,
    recordingState: VideoRecordingState,
    isCapturing: Boolean,
    onClick: () -> Unit,
) {
    val isRecording = recordingState is VideoRecordingState.Recording

    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .border(
                width = 3.dp,
                color = if (mode == MediaType.VIDEO) {
                    if (isRecording) Color.Red else Color.Red.copy(alpha = 0.8f)
                } else {
                    VyraActionCyan
                },
                shape = CircleShape,
            )
            .clickable(enabled = !isCapturing, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (mode == MediaType.IMAGE) {
            // Photo shutter circle
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VyraActionCyan),
            )
        } else {
            // Video record indicator: circle when idle, rounded square when recording
            Box(
                modifier = Modifier
                    .size(if (isRecording) 32.dp else 64.dp)
                    .clip(if (isRecording) RoundedCornerShape(6.dp) else CircleShape)
                    .background(Color.Red),
            )
        }
    }
}

@Composable
private fun PermissionFallbackCard(
    onGrantClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.extraLarge)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.camera_permission_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.camera_permission_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(24.dp))
        VyraButton(
            text = stringResource(R.string.camera_permission_grant),
            onClick = onGrantClick,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(12.dp))
        VyraButton(
            text = stringResource(R.string.camera_permission_denied_settings),
            onClick = onSettingsClick,
            style = VyraButtonStyle.Secondary,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(12.dp))
        VyraButton(
            text = stringResource(R.string.capture_close),
            onClick = onClose,
            style = VyraButtonStyle.Ghost,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
