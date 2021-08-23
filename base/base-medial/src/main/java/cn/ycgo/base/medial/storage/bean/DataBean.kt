package cn.ycgo.base.medial.storage.bean


/**
 *Author:Kgstt
 *Time: 2021/1/8
 */

data class AppConfigBean(
    val is_allow_logout: Int,//退出按钮
    val is_show_insurance: Int,
    val customer_service: CustomerServiceBean,//客服链接
    val wechat_qrcode: String
)

data class CustomerServiceBean(
    val url: String,
    val tel: String
)

data class DebugInfoBean(
    val key: String,
    val value: String
)
