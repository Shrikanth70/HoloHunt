package com.vyra.app.core.media.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import com.vyra.app.core.media.model.MediaType

/**
 * Flash and torch mode for Camera preview and capture.
 */
enum class CameraFlashMode {
    OFF,
    ON,
    AUTO;

    fun toImageCaptureFlashMode(): Int = when (this) {
        OFF -> ImageCapture.FLASH_MODE_OFF
        ON -> ImageCapture.FLASH_MODE_ON
        AUTO -> ImageCapture.FLASH_MODE_AUTO
    }

    fun next(): CameraFlashMode = when (this) {
        OFF -> ON
        ON -> AUTO
        AUTO -> OFF
    }
}

/**
 * Camera lens selector (Back vs Front).
 */
enum class CameraLens(val selector: CameraSelector) {
    BACK(CameraSelector.DEFAULT_BACK_CAMERA),
    FRONT(CameraSelector.DEFAULT_FRONT_CAMERA);

    fun toggle(): CameraLens = when (this) {
        BACK -> FRONT
        FRONT -> BACK
    }
}

/**
 * Current video recording status.
 */
sealed interface VideoRecordingState {
    data object Idle : VideoRecordingState
    data class Recording(val durationSeconds: Int = 0) : VideoRecordingState
    data object Paused : VideoRecordingState
    data class Finalizing(val durationSeconds: Int) : VideoRecordingState
}

/**
 * Aggregate state of the camera viewfinder.
 */
data class CameraState(
    val lens: CameraLens = CameraLens.BACK,
    val flashMode: CameraFlashMode = CameraFlashMode.OFF,
    val mode: MediaType = MediaType.IMAGE,
    val isTorchEnabled: Boolean = false,
    val recordingState: VideoRecordingState = VideoRecordingState.Idle,
    val isTakingPicture: Boolean = false,
    val isCameraReady: Boolean = false,
    val errorMessage: String? = null,
)
