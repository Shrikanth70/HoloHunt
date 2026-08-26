package com.vyra.app.feature.preview

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyra.app.core.media.manager.LocalMediaManager
import com.vyra.app.core.media.model.MediaMetadata
import com.vyra.app.core.media.model.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PreviewUiState(
    val mediaUri: Uri? = null,
    val mediaType: MediaType = MediaType.IMAGE,
    val metadata: MediaMetadata = MediaMetadata(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

@HiltViewModel
class PreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val localMediaManager: LocalMediaManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PreviewUiState())
    val uiState: StateFlow<PreviewUiState> = _uiState.asStateFlow()

    init {
        val uriString: String? = savedStateHandle["mediaUri"]
        val typeString: String? = savedStateHandle["mediaType"]

        if (uriString != null) {
            val uri = Uri.parse(Uri.decode(uriString))
            val type = try {
                MediaType.valueOf(typeString ?: "IMAGE")
            } catch (_: Exception) {
                MediaType.IMAGE
            }
            loadMedia(uri, type)
        } else {
            _uiState.update { it.copy(isLoading = false, errorMessage = "No media URI provided") }
        }
    }

    fun loadMedia(uri: Uri, type: MediaType) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mediaUri = uri, mediaType = type, errorMessage = null) }
            try {
                val metadata = localMediaManager.extractMetadata(uri, type)
                _uiState.update {
                    it.copy(
                        metadata = metadata,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Failed to load media metadata",
                    )
                }
            }
        }
    }
}
