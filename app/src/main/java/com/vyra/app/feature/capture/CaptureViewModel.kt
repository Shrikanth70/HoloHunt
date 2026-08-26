package com.vyra.app.feature.capture

import android.net.Uri
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyra.app.core.media.camera.CameraLens
import com.vyra.app.core.media.camera.CameraManager
import com.vyra.app.core.media.camera.VideoRecordingState
import com.vyra.app.core.media.manager.LocalMediaManager
import com.vyra.app.core.media.model.MediaItem
import com.vyra.app.core.media.model.MediaSource
import com.vyra.app.core.media.model.MediaType
import com.vyra.app.core.media.model.MediaValidationResult
import com.vyra.app.core.media.validator.MediaValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val cameraManager: CameraManager,
    private val localMediaManager: LocalMediaManager,
    private val mediaValidator: MediaValidator,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CaptureUiState())
    val uiState: StateFlow<CaptureUiState> = _uiState.asStateFlow()

    private var activeRecordingFile: File? = null

    fun onPermissionsResult(cameraGranted: Boolean, audioGranted: Boolean) {
        _uiState.update {
            it.copy(
                hasCameraPermission = cameraGranted,
                hasAudioPermission = audioGranted,
            )
        }
    }

    fun bindCamera(lifecycleOwner: LifecycleOwner, previewView: PreviewView) {
        val state = _uiState.value
        cameraManager.bindCamera(
            lifecycleOwner = lifecycleOwner,
            previewView = previewView,
            lens = state.lens,
            onError = { error ->
                _uiState.update { it.copy(errorMessage = error.localizedMessage ?: "Camera binding failed") }
            },
        )
    }

    fun onModeSelected(mode: MediaType) {
        if (_uiState.value.recordingState !is VideoRecordingState.Idle) return
        _uiState.update { it.copy(selectedMode = mode) }
    }

    fun toggleLens() {
        if (_uiState.value.recordingState !is VideoRecordingState.Idle) return
        _uiState.update { it.copy(lens = it.lens.toggle()) }
    }

    fun toggleFlash() {
        _uiState.update { it.copy(flashMode = it.flashMode.next()) }
    }

    fun focusOn(x: Float, y: Float, previewView: PreviewView) {
        cameraManager.focusOnPoint(x, y, previewView)
    }

    fun capturePhoto() {
        if (_uiState.value.isCapturing) return
        _uiState.update { it.copy(isCapturing = true, errorMessage = null) }

        val targetFile = localMediaManager.createTempCaptureFile(MediaType.IMAGE)
        cameraManager.takePhoto(
            targetFile = targetFile,
            flashMode = _uiState.value.flashMode,
        ) { result ->
            viewModelScope.launch {
                result.fold(
                    onSuccess = { file ->
                        val uri = Uri.fromFile(file)
                        val metadata = localMediaManager.extractMetadata(uri, MediaType.IMAGE)
                        val validation = mediaValidator.validate(metadata, MediaType.IMAGE)

                        when (validation) {
                            is MediaValidationResult.Valid -> {
                                val item = MediaItem(
                                    uri = uri,
                                    file = file,
                                    type = MediaType.IMAGE,
                                    source = MediaSource.CAMERA,
                                    metadata = metadata,
                                )
                                _uiState.update {
                                    it.copy(
                                        isCapturing = false,
                                        capturedMedia = item,
                                    )
                                }
                            }
                            is MediaValidationResult.Invalid -> {
                                _uiState.update {
                                    it.copy(
                                        isCapturing = false,
                                        errorMessage = validation.reason,
                                    )
                                }
                            }
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isCapturing = false,
                                errorMessage = error.localizedMessage ?: "Failed to capture photo",
                            )
                        }
                    },
                )
            }
        }
    }

    fun toggleVideoRecording() {
        val currentRecState = _uiState.value.recordingState
        if (currentRecState is VideoRecordingState.Idle) {
            startVideoRecording()
        } else if (currentRecState is VideoRecordingState.Recording) {
            stopVideoRecording()
        }
    }

    private fun startVideoRecording() {
        val targetFile = localMediaManager.createTempCaptureFile(MediaType.VIDEO)
        activeRecordingFile = targetFile
        _uiState.update { it.copy(recordingState = VideoRecordingState.Recording(0), errorMessage = null) }

        cameraManager.startVideoRecording(
            targetFile = targetFile,
            withAudio = _uiState.value.hasAudioPermission,
        ) { event ->
            when (event) {
                is VideoRecordEvent.Status -> {
                    val durationSec = (event.recordingStats.recordedDurationNanos / 1_000_000_000L).toInt()
                    _uiState.update {
                        if (it.recordingState is VideoRecordingState.Recording) {
                            it.copy(recordingState = VideoRecordingState.Recording(durationSec))
                        } else it
                    }
                }
                is VideoRecordEvent.Finalize -> {
                    val file = activeRecordingFile
                    if (!event.hasError() && file != null) {
                        viewModelScope.launch {
                            val uri = Uri.fromFile(file)
                            val metadata = localMediaManager.extractMetadata(uri, MediaType.VIDEO)
                            val validation = mediaValidator.validate(metadata, MediaType.VIDEO)
                            when (validation) {
                                is MediaValidationResult.Valid -> {
                                    val item = MediaItem(
                                        uri = uri,
                                        file = file,
                                        type = MediaType.VIDEO,
                                        source = MediaSource.CAMERA,
                                        metadata = metadata,
                                    )
                                    _uiState.update {
                                        it.copy(
                                            recordingState = VideoRecordingState.Idle,
                                            capturedMedia = item,
                                        )
                                    }
                                }
                                is MediaValidationResult.Invalid -> {
                                    _uiState.update {
                                        it.copy(
                                            recordingState = VideoRecordingState.Idle,
                                            errorMessage = validation.reason,
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                recordingState = VideoRecordingState.Idle,
                                errorMessage = event.cause?.localizedMessage ?: "Video recording failed",
                            )
                        }
                    }
                    activeRecordingFile = null
                }
            }
        }
    }

    private fun stopVideoRecording() {
        val duration = (_uiState.value.recordingState as? VideoRecordingState.Recording)?.durationSeconds ?: 0
        _uiState.update { it.copy(recordingState = VideoRecordingState.Finalizing(duration)) }
        cameraManager.stopVideoRecording()
    }

    fun onGalleryMediaSelected(uri: Uri) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isCapturing = true, errorMessage = null) }
                val importedItem = localMediaManager.importGalleryMedia(uri)
                val validation = mediaValidator.validate(importedItem.metadata, importedItem.type)
                when (validation) {
                    is MediaValidationResult.Valid -> {
                        _uiState.update {
                            it.copy(
                                isCapturing = false,
                                capturedMedia = importedItem,
                            )
                        }
                    }
                    is MediaValidationResult.Invalid -> {
                        _uiState.update {
                            it.copy(
                                isCapturing = false,
                                errorMessage = validation.reason,
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isCapturing = false,
                        errorMessage = e.localizedMessage ?: "Failed to import media",
                    )
                }
            }
        }
    }

    fun consumeCapturedMedia() {
        _uiState.update { it.copy(capturedMedia = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    override fun onCleared() {
        super.onCleared()
        cameraManager.unbind()
    }
}
