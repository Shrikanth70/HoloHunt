package com.vyra.app.feature.capture

import android.net.Uri
import com.vyra.app.core.media.camera.CameraFlashMode
import com.vyra.app.core.media.camera.CameraLens
import com.vyra.app.core.media.camera.VideoRecordingState
import com.vyra.app.core.media.model.MediaItem
import com.vyra.app.core.media.model.MediaType

/**
 * UI State for the Capture screen.
 */
data class CaptureUiState(
    val selectedMode: MediaType = MediaType.IMAGE,
    val lens: CameraLens = CameraLens.BACK,
    val flashMode: CameraFlashMode = CameraFlashMode.AUTO,
    val isTorchActive: Boolean = false,
    val hasCameraPermission: Boolean = false,
    val hasAudioPermission: Boolean = false,
    val isCapturing: Boolean = false,
    val recordingState: VideoRecordingState = VideoRecordingState.Idle,
    val capturedMedia: MediaItem? = null,
    val errorMessage: String? = null,
)
