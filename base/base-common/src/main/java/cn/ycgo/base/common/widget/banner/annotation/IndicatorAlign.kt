package cn.ycgo.base.common.widget.banner.annotation

import androidx.annotation.IntDef
import cn.ycgo.base.common.widget.banner.annotation.Align.BOTTOM
import cn.ycgo.base.common.widget.banner.annotation.Align.LEFT
import cn.ycgo.base.common.widget.banner.annotation.Align.RIGHT
import cn.ycgo.base.common.widget.banner.annotation.Align.TOP


@IntDef(
    value = [LEFT, RIGHT, TOP, BOTTOM],
    flag = true
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class IndicatorAlign