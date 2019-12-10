package com.tongjisse.adventure.data.network.dto


class ScenicSpotDto {
    lateinit var id: String
    lateinit var name: String
    lateinit var type: String
    lateinit var location: String
    lateinit var photos: List<ScenicSpotPhotoDto>
    val imageUrl: String
        get() {
            if (photos.size == 0)
                return "https://c-ssl.duitang.com/uploads/item/201705/07/20170507211114_Txv4P.thumb.700_0.jpeg"
            else return photos[0].url
        }
}
