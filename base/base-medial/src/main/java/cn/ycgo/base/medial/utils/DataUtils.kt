package cn.ycgo.base.medial.utils

import cn.ycgo.base.network.storage.bean.BaseResponse
import cn.ycgo.base.network.storage.bean.ListResponse
import cn.ycgo.base.storage.bean.VMResponse

/**
 *Author:Kgstt
 *Time: 2020/12/22
 */

fun <T> BaseResponse<T>.toVMResponse(): VMResponse<T> {
    return VMResponse<T>(this.result, this.msg, this.data)
}

fun <T> ListResponse<T>.toVMResponse(result:Boolean, msg:String): VMResponse<List<T>> {
    return VMResponse<List<T>>(result, msg, this.data)
}