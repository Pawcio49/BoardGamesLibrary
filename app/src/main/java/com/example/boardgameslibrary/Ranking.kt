package com.example.boardgameslibrary

import java.sql.Date

class Ranking {
    var id: Int = 0
    var gameId: Long = 0
    var date: Date? = null
    var position: Int = 0

    constructor(id: Int, gameId: Long, date: Date, position: Int) {
        this.id = id
        this.gameId = gameId
        this.date = date
        this.position = position
    }
}