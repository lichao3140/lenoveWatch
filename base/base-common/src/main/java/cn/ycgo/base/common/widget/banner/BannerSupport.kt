package cn.ycgo.base.common.widget.banner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cn.ycgo.base.common.widget.banner.decoration.MarginItemDecoration

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */

fun ViewPager2Banner.config(config: BannerConfig.() -> Unit) {
    val bannerConfig = BannerConfig()
    config(bannerConfig)
    setOrientation(bannerConfig.orientation)
    interval = bannerConfig.interval
    bannerConfig.adapter?.let {
        setAdapter(it)
    }
    bannerConfig.transformer?.let {
        setPageTransformer(it)
    }

    if (bannerConfig.itemMargin != 0) {
        addItemDecoration(MarginItemDecoration(bannerConfig.itemMargin))
    }

    bannerConfig.indicator?.let {
        addItemDecoration(it)
    }
}

class BannerConfig {
    var interval: Long = 3000L
    var itemMargin: Int = 0
    var adapter: RecyclerView.Adapter<*>? = null
    var indicator: RecyclerView.ItemDecoration? = null
    var transformer: ViewPager2.PageTransformer? = null
    @ViewPager2.Orientation
    var orientation: Int = ViewPager2.ORIENTATION_HORIZONTAL
}