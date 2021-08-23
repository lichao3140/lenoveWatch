package cn.ycgo.lenovowatch.components.mine.view

import cn.ycgo.lenovowatch.components.mine.R
import cn.ycgo.lenovowatch.components.mine.databinding.FragmentMineBinding
import cn.ycgo.lenovowatch.components.mine.viewmodel.CircleVM
import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.medial.view.BasePlusFragment

/**
 * ```
 * author: Kgstt
 * time  : 6/7/21 4:26 PM
 * ```
 */
class MineFragment:BasePlusFragment<FragmentMineBinding, CircleVM>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_mine
    }

    companion object {
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    override fun initView() {
        super.initView()
        viewBinding?.root?.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0)
    }

}