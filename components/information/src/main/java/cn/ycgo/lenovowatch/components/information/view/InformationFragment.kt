package cn.ycgo.lenovowatch.components.information.view


import com.gyf.immersionbar.ImmersionBar
import cn.ycgo.base.medial.view.BasePlusFragment
import cn.ycgo.lenovowatch.components.information.R
import cn.ycgo.lenovowatch.components.information.databinding.FragmentInformationBinding
import cn.ycgo.lenovowatch.components.information.viewmodel.CircleVM

/**
 * ```
 * author: Kgstt
 * time  : 6/7/21 4:26 PM
 * ```
 */
class InformationFragment:BasePlusFragment<FragmentInformationBinding, CircleVM>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_information
    }

    companion object {
        fun newInstance(): InformationFragment {
            return InformationFragment()
        }
    }

    override fun initView() {
        super.initView()
        viewBinding?.root?.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0)
    }

}