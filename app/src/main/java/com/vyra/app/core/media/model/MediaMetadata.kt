package com.vyra.app.core.media.model

import java.util.Locale

/**
 * Extracted structural metadata for an image or video asset.
 */
data class MediaMetadata(
    val width: Int = 0,
    val height: Int = 0,
    val rotationDegrees: Int = 0,
    val durationMs: Long = 0L,
    val sizeBytes: Long = 0L,
    val mimeType: String = "",
    val timestamp: Long = System.currentTimeMillis(),
) {
    /**
     * Formatted string showing effective dimensions after rotation compensation.
     */
    val effectiveDimensions: Pair<Int, Int>
        get() = if (rotationDegrees == 90 || rotationDegrees == 270) {
            height to width
        } else {
            width to height
        }

    val formattedDimensions: String
        get() {
            val (w, h) = effectiveDimensions
            return if (w > 0 && h > 0) "${w}x${h}" else "Unknown"
        }

    val formattedFileSize: String
        get() {
            if (sizeBytes <= 0) return "0 B"
            val units = arrayOf("B", "KB", "MB", "GB")
            var size = sizeBytes.toDouble()
            var unitIndex = 0
            while (size >= 1024.0 && unitIndex < units.size - 1) {
                size /= 1024.0
                unitIndex++
            }
            return String.format(Locale.US, "%.1f %s", size, units[unitIndex])
        }

    val formattedDuration: String
        get() {
            if (durationMs <= 0) return "0:00"
            val totalSeconds = durationMs / 1000
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            return String.format(Locale.US, "%d:%02d", minutes, seconds)
        }

    val isVideo: Boolean
        get() = mimeType.startsWith("video/") || durationMs > 0L
}
