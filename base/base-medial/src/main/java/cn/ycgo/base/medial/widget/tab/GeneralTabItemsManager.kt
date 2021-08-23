package cn.ycgo.base.medial.widget.tab

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.setGone
import cn.ycgo.base.common.utils.setInVisible
import cn.ycgo.base.common.utils.setVisible
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.storage.bean.GeneralTabItem
import cn.ycgo.base.medial.databinding.ItemGeneralTabIndicatorBinding
import java.util.ArrayList

/**
 *Author:Kgstt
 *Time: 2020/11/27
 */
class GeneralTabItemsManager {
    private val tabItems = ArrayList<GeneralTabItem>()
    private val itemViewBind = ArrayList<ItemGeneralTabIndicatorBinding?>()
    private var tabLayout: TabLayout? = null

    @DrawableRes
    private var defaultIndicatorRes = R.drawable.shape_theme_round2

    @ColorRes
    var selectTextColor = R.color.font_black
    var selectTextSize = 18f
    var selectTextStyle = Typeface.defaultFromStyle(Typeface.BOLD)

    @ColorRes
    var unSelectTextColor = R.color.color_gray_AB
    var unSelectTextSize = 14f
    var unSelectTextStyle = Typeface.defaultFromStyle(Typeface.BOLD)

    fun initTabLayout(tabLayout: TabLayout, viewPager2: ViewPager2, items: ArrayList<GeneralTabItem>) {
        this.tabLayout = tabLayout
        clean()
        this.tabItems.addAll(items)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.customView = addTabItem(tab, items[position])
        }.attach()

        this.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.run { setTabSelect(tab.position) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.run { setTabUnSelect(tab.position) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        if(items.isNotEmpty()) {
            setTabSelect(0)
        }
    }

    fun setIndicatorImageRes(@DrawableRes res: Int) {
        defaultIndicatorRes = res
    }

    private fun clean() {
        itemViewBind.clear()
        tabItems.clear()
        tabLayout?.removeAllTabs()
    }

    private fun addTabItem(tab: TabLayout.Tab, item: GeneralTabItem): View {
        val view = LayoutInflater.from(tab.view.context)
            .inflate(R.layout.item_general_tab_indicator, tab.view, false)
        val itemViewBinding = DataBindingUtil.bind<ItemGeneralTabIndicatorBinding>(view)
        itemViewBinding?.tvTabText?.text = item.title
        if(defaultIndicatorRes!=0) {
            itemViewBinding?.vIndicatorPoint?.setImageResource(defaultIndicatorRes)
        }
        view.tag = itemViewBinding
        itemViewBind.add(itemViewBinding)
        itemViewBinding?.tvTabText?.setTextColor(ContextProvider.getColor(unSelectTextColor))
        itemViewBinding?.tvTabText?.textSize = unSelectTextSize
        itemViewBinding?.tvTabText?.typeface = unSelectTextStyle
        return view
    }

    fun updateText(position:Int,text:String){
        itemViewBind[position]?.tvTabText?.text=text
    }

    fun setTabSelect(position: Int) {
        itemViewBind[position]?.run {
            tvTabText.setTextColor(ContextProvider.getColor(selectTextColor))
            tvTabText.textSize = selectTextSize
            tvTabText.typeface = selectTextStyle
            vIndicatorPoint.setVisible()
        }
    }

    fun setTabUnSelect(position: Int) {
        itemViewBind[position]?.run {
            tvTabText.setTextColor(ContextProvider.getColor(unSelectTextColor))
            tvTabText.textSize = unSelectTextSize
            tvTabText.typeface = unSelectTextStyle
            vIndicatorPoint.setInVisible()
        }
    }

    fun setTabMsgCount(position: Int, msgCount: Int) {
        itemViewBind[position]?.run {
            if (msgCount > 0) {
                tvMsg.setVisible()
                tvMsg.text = msgCount.toString()
            } else {
                tvMsg.setGone()
            }
        }
    }

}