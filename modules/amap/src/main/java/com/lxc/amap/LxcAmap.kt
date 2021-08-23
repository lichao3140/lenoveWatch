package com.lxc.amap

import android.text.TextUtils
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.lxc.amap.storage.bean.LxcPOILocationBean
import com.lxc.amap.storage.bean.PointBean
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.storage.bean.LocationPointBean
import cn.ycgo.base.medial.view.dialog.wheel.LocationHelper
import cn.ycgo.base.storage.bean.VMResponse


/**
 *Author:Kgstt
 *Time: 21-3-11
 */
object LxcAmap {

    var locationInfo: AMapLocation? = null

    fun init() {
        AMapLocationClient.setApiKey("4825317cf3b2cce3807992c91b672ea4")
    }

    /**
     * 定位信息(如果已有已定位对象，会复用)
     */
    fun getFastLocation(location: (AMapLocation) -> Unit) {
        if (locationInfo == null) {
            getLocation(location)
        } else {
            location.invoke(locationInfo!!)
        }
    }

    /**
     * 定位信息
     */
    fun getLocation(location: (AMapLocation) -> Unit) {
        //声明AMapLocationClient类对象
        var mLocationClient: AMapLocationClient? = null
        //声明定位回调监听器
        val mLocationListener = AMapLocationListener(object : (AMapLocation) -> Unit {
            override fun invoke(p1: AMapLocation) {
                if (p1.errorCode == AMapLocation.LOCATION_SUCCESS) {
                    locationInfo = p1
                }
                location.invoke(p1)
                mLocationClient?.onDestroy()
                Log.e("amap", "AMapLocationListener : " + p1.toString())
            }
        })
        //初始化定位
        mLocationClient = AMapLocationClient(ContextProvider.context)
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener)

        //声明AMapLocationClientOption对象
        var mLocationOption: AMapLocationClientOption? = null
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//        设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Device_Sensors
        mLocationOption.isOnceLocation = true
        mLocationOption.isOnceLocationLatest = true

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient.startLocation()
    }

    /**
     * 经纬度转城市城市信息
     */
    fun trapezeConvertToSite(latitude: Double, longitude: Double, even: (LocationPointBean?) -> Unit) {
        val geocoderSearch = GeocodeSearch(ContextProvider.context)
        geocoderSearch.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
            override fun onRegeocodeSearched(result: RegeocodeResult?, rCode: Int) {
                val formatAddress = result?.regeocodeAddress?.formatAddress
                val bean: LocationPointBean? = if (TextUtils.isEmpty(result?.regeocodeAddress?.adCode)) {
                    LocationHelper.findCity(0)
                } else {
                    LocationHelper.findCity(result?.regeocodeAddress?.adCode?.toInt() ?: 0)
                }

                bean?.address = formatAddress.toString()
                even.invoke(bean)
                Log.e("amap", "onRegeocodeSearched : " + result.toString())
            }

            override fun onGeocodeSearched(result: GeocodeResult?, rCode: Int) {
            }
        })
        val lp = LatLonPoint(latitude, longitude)
        val query = RegeocodeQuery(lp, 200f, GeocodeSearch.AMAP)
        geocoderSearch.getFromLocationAsyn(query)
    }

    /**
     * 自己位置周边地址
     */
    fun searchSelfPOIPeripheryAddress(even: (VMResponse<ArrayList<LxcPOILocationBean>>) -> Unit) {
        if (locationInfo == null) {
            getLocation { location ->
                if (location.errorCode == AMapLocation.LOCATION_SUCCESS) {
                    searchPeripheryAddress(longitude = location.longitude, latitude = location.latitude, even = even)
                }
            }
        } else {
            searchPeripheryAddress(longitude = locationInfo!!.longitude, latitude = locationInfo!!.latitude, even = even)
        }
    }

    /**
     * 经纬度位置周边地址
     */
    fun searchPeripheryAddress(latitude: Double, longitude: Double, even: (VMResponse<ArrayList<LxcPOILocationBean>>) -> Unit) {
        val query = PoiSearch.Query("", "", "")
        query.pageSize = 30
        val search = PoiSearch(ContextProvider.context, query)
        search.bound = PoiSearch.SearchBound(LatLonPoint(latitude, longitude), 10000)
        search.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
            }

            override fun onPoiSearched(result: PoiResult, i: Int) {
                val pois = result.pois
                if (pois != null) {
                    val list = ArrayList<LxcPOILocationBean>()
                    for (item in pois) {
                        list.add(
                            LxcPOILocationBean(
                                locationPointBean = PointBean(
                                    provinceName = item.provinceName,
                                    cityName = item.cityName,
                                    countyName = item.adName,
                                    address = item.provinceName + item.cityName + item.adName + item.snippet
                                ),
                                placeName = item.title,
                                latitude = item.latLonPoint.latitude,
                                longitude = item.latLonPoint.longitude
                            )
                        )
                    }
                    even.invoke(VMResponse(result = true, msg = "", extInfo = list))
                } else {
                    even.invoke(VMResponse(result = false, msg = ""))
                }
            }
        })
        search.searchPOIAsyn()
    }

    fun addressSearch(keyword: String, cityName: String, even: (VMResponse<ArrayList<LxcPOILocationBean>>) -> Unit) {
        val mPoiSearchQuery = PoiSearch.Query(keyword, "", cityName)
        mPoiSearchQuery.requireSubPois(true) //true 搜索结果包含POI父子关系; false
        mPoiSearchQuery.pageSize = 30
        mPoiSearchQuery.pageNum = 0
        val poiSearch = PoiSearch(ContextProvider.context, mPoiSearchQuery)
        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
            }

            override fun onPoiSearched(result: PoiResult?, p1: Int) {
                val pois = result?.pois
                if (pois != null) {
                    val list = ArrayList<LxcPOILocationBean>()
                    for (item in pois) {
                        list.add(
                            LxcPOILocationBean(
                                locationPointBean = PointBean(
                                    provinceName = item.provinceName,
                                    cityName = item.cityName,
                                    countyName = item.adName,
                                    address = item.provinceName + item.cityName + item.adName + item.snippet
                                ),
                                placeName = item.title,
                                latitude = item.latLonPoint.latitude,
                                longitude = item.latLonPoint.longitude
                            )
                        )
                    }
                    even.invoke(VMResponse(result = true, msg = "", extInfo = list))
                } else {
                    even.invoke(VMResponse(result = false, msg = ""))
                }
            }
        })
        poiSearch.searchPOIAsyn()
    }

    fun addressSearch(keyword: String, even: (VMResponse<ArrayList<LxcPOILocationBean>>) -> Unit) {
        if (TextUtils.isEmpty(keyword)) {
            searchSelfPOIPeripheryAddress(even)
        } else {
            if (locationInfo == null) {
                getLocation { location ->
                    if (location.errorCode == AMapLocation.LOCATION_SUCCESS) {
                        addressSearch(keyword = keyword, cityName = location.city, even = even)
                    }
                }
            } else {
                addressSearch(keyword = keyword, cityName = locationInfo!!.city, even = even)
            }
        }
    }
}