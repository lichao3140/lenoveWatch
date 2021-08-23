package cn.ycgo.base.common

import cn.ycgo.base.common.storage.enums.ClientType

/**
 *Author:Kgstt
 *Time: 2021/1/11
 */
object DebugConfig {

    /**
     * 开发控制客户端环境
     */
    private var clientType = ClientType.DEBUG //禁止直接修改

    val isDebugMode: Boolean
        get() {
            return BuildConfig.DEBUG
        }

    fun setClientType(type: ClientType) {
        DebugConfig.clientType = type
    }

    fun getClientType(): ClientType = DebugConfig.clientType

}