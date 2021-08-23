package cn.ycgo.base.network.core

import cn.ycgo.base.common.storage.enums.ClientType
import cn.ycgo.base.common.DebugConfig
import cn.ycgo.base.common.utils.EnumConverter
import cn.ycgo.base.common.utils.HasValue
import cn.ycgo.base.common.utils.buildValueMap

/**
 *Author:Kgstt
 *Time: 2020/11/24
 */
enum class Host(override val value: Int) : HasValue<Int> {
    API(1),
    H5(2);

    companion object : EnumConverter<Int, Host>(buildValueMap())

    fun toUrl(): String {
        return when (this) {
            API -> {
                if (DebugConfig.isDebugMode) {
                    when (DebugConfig.getClientType()) {
                        ClientType.DEBUG -> Apis.DEBUG_API
                        ClientType.RELEASE -> Apis.RELEASE_API
                        else -> Apis.DEBUG_API
                    }
                } else {
                    return Apis.RELEASE_API
                }
            }
            H5 -> {
                if (DebugConfig.isDebugMode) {
                    when (DebugConfig.getClientType()) {
                        ClientType.DEBUG -> Apis.DEBUG_H5
                        ClientType.RELEASE -> Apis.RELEASE_H5
                        else -> Apis.DEBUG_H5
                    }
                } else {
                    return Apis.RELEASE_H5
                }
            }
        }
    }
}