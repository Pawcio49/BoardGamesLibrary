package com.example.boardgameslibrary.dto

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList

@ElementList(name = "ranks", required = false)
data class RanksDto(
    @param:Attribute(name = "type", required = false)
    @get:Attribute(name = "type", required = false)
    val type: String? = "",

    @param:Attribute(name = "value", required = false)
    @get:Attribute(name = "value", required = false)
    val value: String? = "0",

    @param:Attribute(name = "id", required = false)
    @get:Attribute(name = "id", required = false)
    val id: String? = "0",

    @param:Attribute(name = "name", required = false)
    @get:Attribute(name = "name", required = false)
    val name: String? = null,

    @param:Attribute(name = "friendlyname", required = false)
    @get:Attribute(name = "friendlyname", required = false)
    val friendlyname: String? = null,

    @param:Attribute(name = "bayesaverage", required = false)
    @get:Attribute(name = "bayesaverage", required = false)
    val bayesaverage: String? = null

)