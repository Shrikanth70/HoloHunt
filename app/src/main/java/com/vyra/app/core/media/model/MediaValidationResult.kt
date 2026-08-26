package com.vyra.app.core.media.model

/**
 * Result of validating an acquired media file before sending to AI extraction or AR.
 */
sealed interface MediaValidationResult {
    data object Valid : MediaValidationResult

    sealed class Invalid(val reason: String) : MediaValidationResult {
        data class UnsupportedFormat(val mimeType: String) :
            Invalid("Unsupported media format: $mimeType")

        data class FileTooLarge(val sizeBytes: Long, val maxBytes: Long) :
            Invalid("File size exceeds limit (${sizeBytes / (1024 * 1024)}MB > ${maxBytes / (1024 * 1024)}MB)")

        data class ResolutionOutOfBounds(val width: Int, val height: Int) :
            Invalid("Resolution is unsupported or corrupted ($width x $height)")

        data class DurationExceeded(val durationMs: Long, val maxDurationMs: Long) :
            Invalid("Video duration exceeds limit (${durationMs / 1000}s > ${maxDurationMs / 1000}s)")

        data class EmptyOrCorrupt(val details: String) :
            Invalid("Media file is empty or corrupt: $details")
    }
}
