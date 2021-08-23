package cn.ycgo.lenovowatch.app.storage.enums

import cn.ycgo.base.common.utils.EnumConverter
import cn.ycgo.base.common.utils.HasValue
import cn.ycgo.base.common.utils.buildValueMap

/**
 *Author:Kgstt
 *Time: 2020/11/26
 */
enum class MainPage(override val value: Int) : HasValue<Int> {
    HOME(0);

    companion object : EnumConverter<Int, MainPage>(buildValueMap())

}