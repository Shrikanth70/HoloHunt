package com.vyra.app.core.media.model

import android.net.Uri
import java.io.File
import java.util.UUID

/**
 * Represents an acquired media asset in the VYRA media pipeline.
 */
data class MediaItem(
    val id: String = UUID.randomUUID().toString(),
    val uri: Uri,
    val file: File? = null,
    val type: MediaType,
    val source: MediaSource,
    val metadata: MediaMetadata = MediaMetadata(),
    val createdAt: Long = System.currentTimeMillis(),
)
