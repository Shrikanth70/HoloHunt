package com.vyra.app.core.media

import com.vyra.app.core.media.model.MediaMetadata
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MediaMetadataTest {

    @Test
    fun effectiveDimensions_accountsForRotation() {
        val landscape = MediaMetadata(width = 1920, height = 1080, rotationDegrees = 0)
        assertEquals(1920 to 1080, landscape.effectiveDimensions)
        assertEquals("1920x1080", landscape.formattedDimensions)

        val rotated90 = MediaMetadata(width = 1920, height = 1080, rotationDegrees = 90)
        assertEquals(1080 to 1920, rotated90.effectiveDimensions)
        assertEquals("1080x1920", rotated90.formattedDimensions)

        val rotated270 = MediaMetadata(width = 1920, height = 1080, rotationDegrees = 270)
        assertEquals(1080 to 1920, rotated270.effectiveDimensions)
    }

    @Test
    fun formattedFileSize_scalesCorrectly() {
        val zero = MediaMetadata(sizeBytes = 0L)
        assertEquals("0 B", zero.formattedFileSize)

        val kb = MediaMetadata(sizeBytes = 1536L)
        assertEquals("1.5 KB", kb.formattedFileSize)

        val mb = MediaMetadata(sizeBytes = 25 * 1024 * 1024L)
        assertEquals("25.0 MB", mb.formattedFileSize)
    }

    @Test
    fun formattedDuration_formatsMinutesAndSeconds() {
        val shortVideo = MediaMetadata(durationMs = 5_000L)
        assertEquals("0:05", shortVideo.formattedDuration)

        val longVideo = MediaMetadata(durationMs = 85_000L)
        assertEquals("1:25", longVideo.formattedDuration)
    }

    @Test
    fun isVideo_detectsVideoMimeOrDuration() {
        val image = MediaMetadata(mimeType = "image/jpeg")
        assertFalse(image.isVideo)

        val videoByMime = MediaMetadata(mimeType = "video/mp4")
        assertTrue(videoByMime.isVideo)

        val videoByDuration = MediaMetadata(durationMs = 10_000L)
        assertTrue(videoByDuration.isVideo)
    }
}
