package cn.ycgo.lenovowatch.app.storage.bean

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
data class MainNavigationItem(@StringRes val title: Int, @DrawableRes val defaultIcon:Int,val selectIcon:Int)