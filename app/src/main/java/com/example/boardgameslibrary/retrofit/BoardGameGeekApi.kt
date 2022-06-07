package com.example.boardgameslibrary.retrofit

import com.example.boardgameslibrary.dto.GameListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardGameGeekApi {
    @GET("/xmlapi2/collection")
    suspend fun getGames(
        @Query("username") userName: String,
        @Query("stats") stats: Int = 1
    ) : GameListDto
}