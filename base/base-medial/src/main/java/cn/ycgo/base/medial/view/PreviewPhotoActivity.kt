package cn.ycgo.base.medial.view

import android.graphics.Color
import cn.ycgo.base.common.widget.banner.annotation.Align.BOTTOM
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.dpToPxOffset
import cn.ycgo.base.common.widget.banner.decoration.DotIndicatorDecoration
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.ActivityPreviewPhotoBinding
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.storage.report.GlobalValues
import cn.ycgo.base.medial.utils.ToastUtil
import cn.ycgo.base.viewmodel.BaseViewModel
import java.util.ArrayList
import cn.ycgo.base.common.widget.adapter.TabFragmentAdapter

/**
 * Author:Kgstt
 * Time: 21-2-1
 * 照片预览
 * 参数 ：
 * GlobalValues.TYPE int 默认展示的index
 * GlobalValues.CONTENT String[] 照片url或者本地路径
 */
@Route(path = PathConstants.ACTIVITY_PREVIEW_PHOTO)
class PreviewPhotoActivity : BasePlusActivity<ActivityPreviewPhotoBinding, BaseViewModel>() {

    private val fragments = ArrayList<PreviewPhotoFragment>()
    private lateinit var photos: ArrayList<String>
    private var index: Int = 0

    override fun getLayoutResId(): Int {
        return R.layout.activity_preview_photo
    }

    override fun getImmersionBarSetting(): ImmersionBar {
        return super.getImmersionBarSetting().statusBarColor(R.color.color_transparency).navigationBarColor(R.color.color_black)
    }

    override fun onViewLoadFinish() {
        super.onViewLoadFinish()
        ContextProvider.setStatusBarTextColor(window, true)
    }

    override fun initView() {
        setActivityBackgroundColor(Color.BLACK)
        super.initView()
        viewBinding?.vBack?.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0)
        viewBinding?.vBack?.setColorFilter(Color.WHITE)
        index = intent.getIntExtra(GlobalValues.TYPE, 0)
        photos = intent.getStringArrayListExtra(GlobalValues.CONTENT) ?: ArrayList<String>()

        if (photos.isEmpty()) {
            ToastUtil.show(R.string.tip_error_layout_default)
            finish()
        }

        photos.forEach {
            fragments.add(PreviewPhotoFragment.getInstance(it))
        }

        viewBinding?.run {
            if (fragments.size > 1) {
                viewPager.addItemDecoration(
                    DotIndicatorDecoration(
                        align = BOTTOM,
                        selectedColor = ContextProvider.getColor(R.color.color_theme),
                        space = 4.dpToPxOffset(),
                        isInfinite = false
                    )
                )
                viewPager.offscreenPageLimit=fragments.size
            }
            viewPager.adapter = TabFragmentAdapter(this@PreviewPhotoActivity, fragments)
            viewPager.setCurrentItem(index, false)
        }
    }

    override fun initViewListener() {
        super.initViewListener()
        viewBinding?.vBack?.setOnClickListener {
            finish()
        }
    }
}