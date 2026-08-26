package com.vyra.app.core.media.manager

import android.content.Context
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.vyra.app.core.media.model.MediaItem
import com.vyra.app.core.media.model.MediaMetadata
import com.vyra.app.core.media.model.MediaSource
import com.vyra.app.core.media.model.MediaType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages local temporary file storage, gallery ingestion, metadata extraction,
 * and scoped cleanup for the VYRA media pipeline.
 */
@Singleton
class LocalMediaManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val captureDir: File
        get() = File(context.cacheDir, "captures").apply { if (!exists()) mkdirs() }

    private val importDir: File
        get() = File(context.cacheDir, "imports").apply { if (!exists()) mkdirs() }

    /**
     * Creates a new temporary file in the scoped cache for camera photo/video capture.
     */
    fun createTempCaptureFile(type: MediaType): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.US).format(Date())
        val extension = when (type) {
            MediaType.IMAGE -> ".jpg"
            MediaType.VIDEO -> ".mp4"
        }
        return File(captureDir, "VYRA_${timestamp}$extension")
    }

    /**
     * Extracts full metadata from a given [Uri].
     */
    suspend fun extractMetadata(uri: Uri, type: MediaType): MediaMetadata =
        withContext(Dispatchers.IO) {
            val mimeType = context.contentResolver.getType(uri) ?: when (type) {
                MediaType.IMAGE -> "image/jpeg"
                MediaType.VIDEO -> "video/mp4"
            }

            var sizeBytes = 0L
            try {
                context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
                    sizeBytes = pfd.statSize
                }
            } catch (_: Exception) {
                // Fallback to reading file if uri is file scheme
                if (uri.scheme == "file") {
                    uri.path?.let { path ->
                        sizeBytes = File(path).length()
                    }
                }
            }

            when (type) {
                MediaType.IMAGE -> extractImageMetadata(uri, mimeType, sizeBytes)
                MediaType.VIDEO -> extractVideoMetadata(uri, mimeType, sizeBytes)
            }
        }

    private fun extractImageMetadata(uri: Uri, mimeType: String, sizeBytes: Long): MediaMetadata {
        var width = 0
        var height = 0
        var rotation = 0

        try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }
                BitmapFactory.decodeStream(stream, null, options)
                width = options.outWidth
                height = options.outHeight
            }
        } catch (_: Exception) { }

        try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val exif = ExifInterface(stream)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL,
                )
                rotation = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
            }
        } catch (_: Exception) { }

        return MediaMetadata(
            width = width,
            height = height,
            rotationDegrees = rotation,
            durationMs = 0L,
            sizeBytes = sizeBytes,
            mimeType = mimeType,
        )
    }

    private fun extractVideoMetadata(uri: Uri, mimeType: String, sizeBytes: Long): MediaMetadata {
        var width = 0
        var height = 0
        var rotation = 0
        var durationMs = 0L

        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, uri)
            width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull() ?: 0
            height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull() ?: 0
            rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)?.toIntOrNull() ?: 0
            durationMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: 0L
        } catch (_: Exception) {
        } finally {
            try {
                retriever.release()
            } catch (_: Exception) { }
        }

        return MediaMetadata(
            width = width,
            height = height,
            rotationDegrees = rotation,
            durationMs = durationMs,
            sizeBytes = sizeBytes,
            mimeType = mimeType,
        )
    }

    /**
     * Imports a user-selected gallery URI into a local cache file for resilient offline processing.
     */
    suspend fun importGalleryMedia(uri: Uri): MediaItem = withContext(Dispatchers.IO) {
        val mime = context.contentResolver.getType(uri) ?: "image/jpeg"
        val isVideo = mime.startsWith("video/")
        val type = if (isVideo) MediaType.VIDEO else MediaType.IMAGE
        val extension = if (isVideo) ".mp4" else ".jpg"

        val targetFile = File(importDir, "import_${UUID.randomUUID()}$extension")

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(targetFile).use { output ->
                input.copyTo(output)
            }
        }

        val targetUri = Uri.fromFile(targetFile)
        val metadata = extractMetadata(targetUri, type)

        MediaItem(
            uri = targetUri,
            file = targetFile,
            type = type,
            source = MediaSource.GALLERY,
            metadata = metadata,
        )
    }

    /**
     * Deletes a temporary file or item.
     */
    suspend fun deleteMediaItem(item: MediaItem): Boolean = withContext(Dispatchers.IO) {
        item.file?.delete() ?: false
    }

    /**
     * Cleans up temporary capture and import files older than [maxAgeMs].
     */
    suspend fun cleanupOldTemporaryFiles(maxAgeMs: Long = 24 * 60 * 60 * 1000L): Int =
        withContext(Dispatchers.IO) {
            val threshold = System.currentTimeMillis() - maxAgeMs
            var deletedCount = 0
            listOf(captureDir, importDir).forEach { dir ->
                dir.listFiles()?.forEach { file ->
                    if (file.lastModified() < threshold) {
                        if (file.delete()) deletedCount++
                    }
                }
            }
            deletedCount
        }
}
