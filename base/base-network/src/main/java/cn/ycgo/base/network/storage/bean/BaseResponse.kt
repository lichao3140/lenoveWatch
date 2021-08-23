package cn.ycgo.base.network.storage.bean


/**
 *Author:Kgstt
 *Time: 2020/11/24
 */
data class BaseResponse<T>(
    var code: Int = 1,
    var result: Boolean,
    var msg: String? = null,
    var data: T? = null
)

data class ListResponse<T>(
    val current_page: Int,
    val data: List<T>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
)