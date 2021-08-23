package cn.ycgo.lenovewatch.modules.circle.view

import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.medial.view.BasePlusFragment
import com.ycgo.modules.circle.R
import cn.ycgo.lenovewatch.modules.circle.viewmodel.CircleVM

/**
 * ```
 * author: Kgstt
 * time  : 6/7/21 4:26 PM
 * ```
 */
class CircleFragment:BasePlusFragment<com.ycgo.modules.circle.databinding.FragmentCircleBinding, CircleVM>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_circle
    }

    companion object {
        fun newInstance(): CircleFragment {
            return CircleFragment()
        }
    }

    override fun initView() {
        super.initView()
        viewBinding?.root?.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0)
    }

}