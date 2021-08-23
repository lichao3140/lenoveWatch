package cn.ycgo.base.medial.adatper

import android.net.Uri
import android.widget.FrameLayout
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.dpToPx
import cn.ycgo.base.common.utils.dpToPxOffset
import cn.ycgo.base.common.widget.CornerImageView
import cn.ycgo.base.common.widget.banner.BaseBannerAdapter
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.storage.bean.IBannerBean
import cn.ycgo.base.medial.utils.ImageLoadUtil

/**
 *Author:Kgstt
 *Time: 2020/11/27
 */
class ImageBannerAdapter : BaseBannerAdapter<IBannerBean>() {

    var imageHeight: Float = 172f
    var cornerSize: Float = 8f
    override fun getItemLayout(position: Int): Int {
        return R.layout.item_banner_image
    }

    override fun bindData(helper: ItemHelper, data: IBannerBean) {
        val uri = Uri.parse(data.getBannerImage())
        val imageView = helper.itemView.findViewById<CornerImageView>(R.id.iv_banner_img)
        imageView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, imageHeight.dpToPxOffset())
        imageView.corner = cornerSize.dpToPx()
        ImageLoadUtil.loadPhoto(ContextProvider.context, uri, imageView = imageView)
    }

    fun buildImageConfig(imageHeightDp: Float, cornerDp: Float) {
        imageHeight = imageHeightDp
        cornerSize = cornerDp
    }
}