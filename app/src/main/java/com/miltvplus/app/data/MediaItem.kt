package com.miltvplus.app.data

enum class MediaKind { MOVIE, SERIES, LIVE_CHANNEL, SPORTS_CHANNEL }

data class MediaItem(
    val id: String,
    val title: String,
    val kind: MediaKind,
    val description: String,
    val category: String,
    val year: Int? = null,
    val durationMinutes: Int? = null,
    val posterUrl: String? = null,
    val trailerUrl: String? = null,
    val playbackUrl: String? = null,
    val isLicensed: Boolean = false,
) {
    val canPlay: Boolean get() = isLicensed && !playbackUrl.isNullOrBlank()
    val canShowTrailer: Boolean get() = !trailerUrl.isNullOrBlank()
}
