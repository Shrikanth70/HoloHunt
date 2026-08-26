package com.vyra.app.core.media

import com.vyra.app.core.media.model.MediaMetadata
import com.vyra.app.core.media.model.MediaType
import com.vyra.app.core.media.model.MediaValidationResult
import com.vyra.app.core.media.validator.MediaValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MediaValidatorTest {

    private lateinit var validator: MediaValidator

    @Before
    fun setUp() {
        validator = MediaValidator()
    }

    @Test
    fun validImage_passesValidation() {
        val metadata = MediaMetadata(
            width = 1920,
            height = 1080,
            sizeBytes = 2 * 1024 * 1024L, // 2 MB
            mimeType = "image/jpeg",
        )
        val result = validator.validate(metadata, MediaType.IMAGE)
        assertEquals(MediaValidationResult.Valid, result)
    }

    @Test
    fun emptyFile_failsValidation() {
        val metadata = MediaMetadata(
            width = 100,
            height = 100,
            sizeBytes = 0L,
            mimeType = "image/jpeg",
        )
        val result = validator.validate(metadata, MediaType.IMAGE)
        assertTrue(result is MediaValidationResult.Invalid.EmptyOrCorrupt)
    }

    @Test
    fun imageTooLarge_failsValidation() {
        val metadata = MediaMetadata(
            width = 1920,
            height = 1080,
            sizeBytes = 60L * 1024 * 1024, // 60 MB > 50 MB limit
            mimeType = "image/png",
        )
        val result = validator.validate(metadata, MediaType.IMAGE)
        assertTrue(result is MediaValidationResult.Invalid.FileTooLarge)
    }

    @Test
    fun unsupportedMime_failsValidation() {
        val metadata = MediaMetadata(
            width = 1920,
            height = 1080,
            sizeBytes = 1024L,
            mimeType = "application/pdf",
        )
        val result = validator.validate(metadata, MediaType.IMAGE)
        assertTrue(result is MediaValidationResult.Invalid.UnsupportedFormat)
    }

    @Test
    fun videoDurationExceeded_failsValidation() {
        val metadata = MediaMetadata(
            width = 1920,
            height = 1080,
            sizeBytes = 10 * 1024 * 1024L,
            durationMs = 90_000L, // 90s > 60s limit
            mimeType = "video/mp4",
        )
        val result = validator.validate(metadata, MediaType.VIDEO)
        assertTrue(result is MediaValidationResult.Invalid.DurationExceeded)
    }

    @Test
    fun validVideo_passesValidation() {
        val metadata = MediaMetadata(
            width = 1080,
            height = 1920,
            sizeBytes = 25 * 1024 * 1024L,
            durationMs = 15_000L, // 15s
            mimeType = "video/mp4",
        )
        val result = validator.validate(metadata, MediaType.VIDEO)
        assertEquals(MediaValidationResult.Valid, result)
    }

    @Test
    fun resolutionOutOfBounds_failsValidation() {
        val metadata = MediaMetadata(
            width = 30, // below MIN_DIMENSION (64)
            height = 1080,
            sizeBytes = 1024L,
            mimeType = "image/jpeg",
        )
        val result = validator.validate(metadata, MediaType.IMAGE)
        assertTrue(result is MediaValidationResult.Invalid.ResolutionOutOfBounds)
    }
}
