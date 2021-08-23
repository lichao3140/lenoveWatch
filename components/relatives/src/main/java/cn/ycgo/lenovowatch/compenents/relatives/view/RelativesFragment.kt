package cn.ycgo.lenovowatch.compenents.relatives.view

import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.medial.view.BasePlusFragment
import cn.ycgo.base.viewmodel.BaseViewModel
import cn.ycgo.lenovowatch.compenents.relatives.R
import cn.ycgo.lenovowatch.compenents.relatives.databinding.FragmentRelativesBinding
import cn.ycgo.lenovowatch.compenents.relatives.viewmodel.CircleVM

/**
 * ```
 * author: Kgstt
 * time  : 6/7/21 4:26 PM
 * ```
 */
class RelativesFragment:BasePlusFragment<FragmentRelativesBinding, CircleVM>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_relatives
    }

    companion object {
        fun newInstance(): RelativesFragment {
            return RelativesFragment()
        }
    }

    override fun initView() {
        super.initView()
        viewBinding?.root?.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0)
    }

}