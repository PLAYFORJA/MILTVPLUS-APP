package com.miltvplus.app.data

/**
 * Catalog boundary. Replace the demo repository with an HTTPS API once a backend
 * and authorized content sources are available. No stream URL or credential belongs
 * in the Android client source code.
 */
interface CatalogRepository {
    suspend fun getMovies(): List<MediaItem>
    suspend fun getSeries(): List<MediaItem>
    suspend fun getLiveChannels(): List<MediaItem>
    suspend fun getSportsChannels(): List<MediaItem>
}

class DemoCatalogRepository : CatalogRepository {
    override suspend fun getMovies() = emptyList<MediaItem>()
    override suspend fun getSeries() = emptyList<MediaItem>()
    override suspend fun getLiveChannels() = emptyList<MediaItem>()
    override suspend fun getSportsChannels() = emptyList<MediaItem>()
}
