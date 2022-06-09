package com.example.boardgameslibrary.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class GameDto(
    @field:Attribute(name = "objectid", required = false)
    @param:Attribute(name = "objectid", required = false)
    var objectId: String? = "",

    @field:Element(name = "name", required = false)
    @param:Element(name = "name", required = false)
    var title: String? = null,

    @field:Element(name = "thumbnail", required = false)
    @param:Element(name = "thumbnail", required = false)
    var thumbnail: String? = null,

    @field:Element(name = "yearpublished", required = false)
    @param:Element(name = "yearpublished", required = false)
    var publishmentYear: String? = null,

    @field:Element(name = "originalname", required = false)
    @param:Element(name = "originalname", required = false)
    var originalName: String? = null,


    @field:Element(name = "stats", required = false)
    @param:Element(name = "stats", required = false)
    var stats: StatsDto? = null
)