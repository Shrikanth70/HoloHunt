package com.vyra.app.core.media.validator

import com.vyra.app.core.media.model.MediaMetadata
import com.vyra.app.core.media.model.MediaType
import com.vyra.app.core.media.model.MediaValidationResult
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Validates media files against size, format, and dimension constraints
 * before passing them to the AI pipeline or AR engine.
 */
@Singleton
class MediaValidator @Inject constructor() {

    companion object {
        const val MAX_IMAGE_SIZE_BYTES = 50L * 1024L * 1024L // 50 MB
        const val MAX_VIDEO_SIZE_BYTES = 200L * 1024L * 1024L // 200 MB
        const val MAX_VIDEO_DURATION_MS = 60_000L // 60 seconds
        const val MIN_DIMENSION = 64
        const val MAX_DIMENSION = 8192

        val SUPPORTED_IMAGE_MIMES = setOf(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/webp",
            "image/heic",
            "image/heif",
        )

        val SUPPORTED_VIDEO_MIMES = setOf(
            "video/mp4",
            "video/webm",
            "video/quicktime",
            "video/3gpp",
        )
    }

    /**
     * Validates [MediaMetadata] according to [type].
     */
    fun validate(metadata: MediaMetadata, type: MediaType): MediaValidationResult {
        // Check size
        if (metadata.sizeBytes <= 0L) {
            return MediaValidationResult.Invalid.EmptyOrCorrupt("File size is 0 bytes")
        }

        val maxAllowedSize = when (type) {
            MediaType.IMAGE -> MAX_IMAGE_SIZE_BYTES
            MediaType.VIDEO -> MAX_VIDEO_SIZE_BYTES
        }
        if (metadata.sizeBytes > maxAllowedSize) {
            return MediaValidationResult.Invalid.FileTooLarge(metadata.sizeBytes, maxAllowedSize)
        }

        // Check MIME type
        val normalizedMime = metadata.mimeType.lowercase().trim()
        val isSupportedMime = when (type) {
            MediaType.IMAGE -> SUPPORTED_IMAGE_MIMES.contains(normalizedMime) || normalizedMime.startsWith("image/")
            MediaType.VIDEO -> SUPPORTED_VIDEO_MIMES.contains(normalizedMime) || normalizedMime.startsWith("video/")
        }
        if (!isSupportedMime && normalizedMime.isNotEmpty()) {
            return MediaValidationResult.Invalid.UnsupportedFormat(metadata.mimeType)
        }

        // Check dimensions if available
        if (metadata.width > 0 || metadata.height > 0) {
            if (metadata.width < MIN_DIMENSION || metadata.height < MIN_DIMENSION ||
                metadata.width > MAX_DIMENSION || metadata.height > MAX_DIMENSION
            ) {
                return MediaValidationResult.Invalid.ResolutionOutOfBounds(
                    metadata.width,
                    metadata.height,
                )
            }
        }

        // Check video duration
        if (type == MediaType.VIDEO && metadata.durationMs > MAX_VIDEO_DURATION_MS) {
            return MediaValidationResult.Invalid.DurationExceeded(
                metadata.durationMs,
                MAX_VIDEO_DURATION_MS,
            )
        }

        return MediaValidationResult.Valid
    }
}
