package com.example.boardgameslibrary.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class GameDto(
    @field:Attribute(name = "objectid", required = false)
    @param:Attribute(name = "objectid", required = false)
    var id: String? = "",

    @field:Element(name = "name", required = false)
    @param:Element(name = "name", required = false)
    var title: String? = null,

    @field:Element(name = "image", required = false)
    @param:Element(name = "image", required = false)
    var image: String? = null,

    @field:Element(name = "yearpublished", required = false)
    @param:Element(name = "yearpublished", required = false)
    var year: String? = null,
)