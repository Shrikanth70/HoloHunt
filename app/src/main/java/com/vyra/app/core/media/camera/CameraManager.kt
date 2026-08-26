package com.vyra.app.core.media.camera

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Controller for CameraX preview, photo capture, and video recording.
 */
@Singleton
class CameraManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var activeRecording: Recording? = null

    private val mainExecutor: Executor
        get() = ContextCompat.getMainExecutor(context)

    suspend fun getCameraProvider(): ProcessCameraProvider =
        suspendCancellableCoroutine { continuation ->
            val future = ProcessCameraProvider.getInstance(context)
            future.addListener(
                {
                    val provider = future.get()
                    cameraProvider = provider
                    continuation.resume(provider)
                },
                mainExecutor,
            )
        }

    /**
     * Binds CameraX preview and capture use cases to the lifecycle owner.
     */
    fun bindCamera(
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        lens: CameraLens = CameraLens.BACK,
        onReady: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
    ) {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener(
            {
                try {
                    val provider = providerFuture.get()
                    cameraProvider = provider
                    provider.unbindAll()

                    val preview = Preview.Builder()
                        .build()
                        .also {
                            it.surfaceProvider = previewView.surfaceProvider
                        }

                    imageCapture = ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                        .build()

                    val recorder = Recorder.Builder()
                        .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                        .build()
                    videoCapture = VideoCapture.withOutput(recorder)

                    camera = provider.bindToLifecycle(
                        lifecycleOwner,
                        lens.selector,
                        preview,
                        imageCapture,
                        videoCapture,
                    )

                    onReady()
                } catch (e: Exception) {
                    onError(e)
                }
            },
            mainExecutor,
        )
    }

    /**
     * Captures a high-resolution still image to [targetFile].
     */
    fun takePhoto(
        targetFile: File,
        flashMode: CameraFlashMode,
        onResult: (Result<File>) -> Unit,
    ) {
        val capture = imageCapture ?: run {
            onResult(Result.failure(IllegalStateException("ImageCapture not bound")))
            return
        }

        capture.flashMode = flashMode.toImageCaptureFlashMode()

        val outputOptions = ImageCapture.OutputFileOptions.Builder(targetFile).build()

        capture.takePicture(
            outputOptions,
            mainExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onResult(Result.success(targetFile))
                }

                override fun onError(exception: ImageCaptureException) {
                    onResult(Result.failure(exception))
                }
            },
        )
    }

    /**
     * Starts recording video to [targetFile].
     */
    @SuppressLint("MissingPermission")
    fun startVideoRecording(
        targetFile: File,
        withAudio: Boolean = true,
        onEvent: (VideoRecordEvent) -> Unit,
    ) {
        val videoCap = videoCapture ?: return
        val outputOptions = FileOutputOptions.Builder(targetFile).build()

        var pendingRecording = videoCap.output
            .prepareRecording(context, outputOptions)

        if (withAudio) {
            try {
                pendingRecording = pendingRecording.withAudioEnabled()
            } catch (_: SecurityException) { }
        }

        activeRecording = pendingRecording.start(mainExecutor) { event ->
            onEvent(event)
            if (event is VideoRecordEvent.Finalize) {
                activeRecording = null
            }
        }
    }

    /**
     * Stops the active video recording.
     */
    fun stopVideoRecording() {
        activeRecording?.stop()
        activeRecording = null
    }

    /**
     * Toggles torch (flashlight) state.
     */
    fun toggleTorch(enabled: Boolean) {
        camera?.cameraControl?.enableTorch(enabled)
    }

    /**
     * Performs tap-to-focus and metering at the given normalized coordinates.
     */
    fun focusOnPoint(x: Float, y: Float, previewView: PreviewView) {
        val control: CameraControl = camera?.cameraControl ?: return
        val factory = previewView.meteringPointFactory
        val point = factory.createPoint(x, y)
        val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF or FocusMeteringAction.FLAG_AE)
            .setAutoCancelDuration(3, TimeUnit.SECONDS)
            .build()
        control.startFocusAndMetering(action)
    }

    fun unbind() {
        cameraProvider?.unbindAll()
        activeRecording?.stop()
        activeRecording = null
    }
}
