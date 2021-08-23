package cn.ycgo.base.common.storage.enums

import cn.ycgo.base.common.utils.EnumConverter
import cn.ycgo.base.common.utils.HasValue
import cn.ycgo.base.common.utils.buildValueMap

/**
 *Author:Kgstt
 *Time: 2021/1/11
 */
enum class ClientType(override val value: Int) : HasValue<Int> {
    DEBUG(1), // 测试环境
    RELEASE(2) ,  // 线上环境
    ;

    companion object : EnumConverter<Int, ClientType>(buildValueMap())
}