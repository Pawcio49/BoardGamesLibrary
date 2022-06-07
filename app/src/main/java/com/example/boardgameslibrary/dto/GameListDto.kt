package com.example.boardgameslibrary.dto

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "items", strict = false)
data class GameListDto (
    @field:ElementList(name = "item", inline = true)
    @param:ElementList(name = "item", inline = true)
    var item: List<GameDto>? = null
)