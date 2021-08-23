package cn.ycgo.base.storage.bean
/**
 *Author:Kgstt
 *Time: 2020/11/25
 *ViewModule通过LiveData传递的实体
 */
data class VMResponse<T>(val result: Boolean, val msg: String?="", val extInfo: T? = null)