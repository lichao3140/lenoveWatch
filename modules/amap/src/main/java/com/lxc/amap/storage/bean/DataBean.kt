package com.lxc.amap.storage.bean

/**
 *Author:Kgstt
 *Time: 21-3-12
 */

data class LxcPOILocationBean(
    val locationPointBean: PointBean,
    val placeName:String,
    var longitude: Double? = 0.0,
    var latitude: Double? = 0.0
)

data class PointBean(
    val provinceName:String,
    val cityName:String,
    val countyName:String,
    var address:String=""
)