package cn.ycgo.base.medial.api

import cn.ycgo.base.medial.storage.bean.AppConfigBean
import cn.ycgo.base.medial.storage.bean.ProvinceBean
import cn.ycgo.base.network.storage.bean.BaseResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

/**
 *Author:Kgstt
 *Time: 2021/1/4
 */
interface IMedialApi {

    /**
     * 获取地区码
     */
    @GET("area/tree")
    fun getAddressCodeAsync(): Deferred<Response<BaseResponse<List<ProvinceBean>>>>

    /**
     * 获取配置
     */
    @GET("app/config")
    fun getAppConfigAsync(): Deferred<Response<BaseResponse<AppConfigBean>>>
}