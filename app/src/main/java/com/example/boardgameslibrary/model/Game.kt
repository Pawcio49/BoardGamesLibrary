package com.example.boardgameslibrary.model

class Game {
    var id: Long = 0
    var title: String? = null
    var originalTitle: String? = null
    var publishmentYear: Int = 0
    var currentRankingPosition: Int = 0
    var image: String? = null

    constructor(
        id: Long,
        title: String,
        originalTitle: String,
        publishmentYear: Int,
        currentRankingPosition: Int,
        image: String
    ) {
        this.id = id
        this.title = title
        this.originalTitle = originalTitle
        this.publishmentYear = publishmentYear
        this.currentRankingPosition = currentRankingPosition
        this.image = image
    }
}