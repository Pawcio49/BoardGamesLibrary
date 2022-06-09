package com.example.boardgameslibrary.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rating")
data class RatingDto(
    @param:ElementList(name = "ranks", required = false)
    @get:ElementList(name = "ranks", required = false)
    val ranks: List<RanksDto>? = null,

    @param:Attribute(name = "value", required = false)
    @get:Attribute(name = "value", required = false)
    val value: String? = "0",

    @param:Element(name = "usersrated", required = false)
    @get:Element(name = "usersrated", required = false)
    val usersrated: String? = null,

    @param:Element(name = "bayesaverage", required = false)
    @get:Element(name = "bayesaverage", required = false)
    val bayesaverage: String? = null,

    @param:Element(name = "stddev", required = false)
    @get:Element(name = "stddev", required = false)
    val stddev: String? = null,

    @param:Element(name = "average", required = false)
    @get:Element(name = "average", required = false)
    val average: String? = null,

    @param:Element(name = "median", required = false)
    @get:Element(name = "median", required = false)
    val median: String? = null
)