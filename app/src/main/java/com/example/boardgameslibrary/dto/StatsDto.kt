package com.example.boardgameslibrary.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name= "stats")
data class StatsDto (
    @param:Attribute(name = "minplayers", required = false)
    @get:Attribute(name = "minplayers", required = false)
    val minplayers: String? = "1",

    @param:Attribute(name = "maxplayers", required = false)
    @get:Attribute(name = "maxplayers", required = false)
    val maxplayers: String? = "100",

    @param:Attribute(name = "numowned", required = false)
    @get:Attribute(name = "numowned", required = false)
    val numowned: String? = "1",

    @param:Attribute(name = "minplaytime", required = false)
    @get:Attribute(name = "minplaytime", required = false)
    val minplaytime: String? = "1",

    @param:Attribute(name = "maxplaytime", required = false)
    @get:Attribute(name = "maxplaytime", required = false)
    val maxplaytime: String? = "1",

    @param:Attribute(name = "playingtime", required = false)
    @get:Attribute(name = "playingtime", required = false)
    val playingtime: String? = "1",

    @field:Element(name = "rating", required = false)
    @param:Element(name = "rating", required = false)
    var rating: RatingDto? = null
    )