package cn.ycgo.base.common.widget.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
class TabFragmentAdapter : FragmentStateAdapter {
    private var fragments = mutableListOf<Fragment>()

    @Suppress("unused")
    constructor(fragmentActivity: FragmentActivity, fragments: List<Fragment>) : super(fragmentActivity) {
        init(fragments)
    }

    @Suppress("unused")
    constructor(fragment: Fragment, fragments: List<Fragment>) : super(fragment) {
        init(fragments)
    }

    @Suppress("unused")
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragments: List<Fragment>) : super(fragmentManager, lifecycle) {
        init(fragments)
    }

    private fun init(fragments: List<Fragment>) {
        this.fragments.addAll(fragments)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}