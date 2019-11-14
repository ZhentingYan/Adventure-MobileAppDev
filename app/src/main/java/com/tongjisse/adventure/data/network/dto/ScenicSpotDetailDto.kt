package com.tongjisse.adventure.data.network.dto

class ScenicSpotDetailDto {
    lateinit var id: String
    lateinit var name: String
    lateinit var location: String
    lateinit var type:String
    lateinit var address:String
    lateinit var deep_info:DeepInfoDto

    val  intro:String?
         get()=deep_info!!.intro
}
