package cn.ycgo.base.medial.storage.bean

import android.text.TextUtils

/**
 *Author:Kgstt
 *Time: 21-3-2
 */

/**
 * 省
 */
data class ProvinceBean(
    val child: List<CityBean>?,
    val id: Int,
    private val name: String,
    private val short_name: String,
    val parent_id: Int
) {
    fun getName(): String {
        return if (TextUtils.isEmpty(name)) short_name else name
    }
}

/**
 * 市
 */
data class CityBean(
    val child: List<CountyBean>?,
    val id: Int,
    private val name: String,
    private val short_name: String,
    val parent_id: Int
) {
    fun getName(): String {
        return if (TextUtils.isEmpty(name)) short_name else name
    }
}

/**
 * 区
 */
data class CountyBean(
    val id: Int,
    private val name: String,
    private val short_name: String,
    val parent_id: Int
) {
    fun getName(): String {
        return if (TextUtils.isEmpty(name)) short_name else name
    }
}

/**
 * 直系地点
 */
data class LocationPointBean(
    var provinceName: String,
    var provinceCode: Int = 0,
    var cityName: String,
    var cityCode: Int = 0,
    var countyName: String,
    var countyCode: Int,
    var address: String = ""
)

