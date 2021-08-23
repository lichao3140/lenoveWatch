package cn.ycgo.lenovowatch.app.view

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.ycgo.app.R
import cn.ycgo.lenovowatch.app.storage.bean.MainNavigationItem
import cn.ycgo.lenovowatch.app.storage.enums.MainPage
import cn.ycgo.base.common.annotation.BindEventBus
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.widget.adapter.TabFragmentAdapter
import com.ycgo.app.databinding.ActivityMainBinding
import com.ycgo.app.databinding.ItemMainNavigationBinding
import cn.ycgo.base.medial.AppConfig
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.router.ServiceFactory
import cn.ycgo.base.medial.utils.ToastUtil
import cn.ycgo.base.medial.view.BaseBoostActivity
import cn.ycgo.base.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList

@BindEventBus
@Route(path = PathConstants.ACTIVITY_MAIN)
class MainActivity : BaseBoostActivity<ActivityMainBinding, BaseViewModel>() {
    override fun getLayoutResId() = R.layout.activity_main
    private val fragments = ArrayList<Fragment>()
    private val navigationItems = ArrayList<MainNavigationItem>()
    private val itemViewBind = ArrayList<ItemMainNavigationBinding?>()
    private var selectedPage = MainPage.HOME

    override fun getImmersionBarSetting(): ImmersionBar {
        return super.getImmersionBarSetting()
            .transparentStatusBar()
            .hideBar(BarHide.FLAG_SHOW_BAR)
    }

    override fun initView() {
        super.initView()

        val viewPager = viewBinding?.viewPager
        viewPager?.isUserInputEnabled = false
        loadPage()
    }

    override fun onDebouncingClick(view: View) {

    }

    private fun loadPage() {
        navigationItems.clear()
        fragments.clear()
        ServiceFactory.homeService?.createHomeFragment()?.run {
            fragments.add(this)
            navigationItems.add(
                MainNavigationItem(
                    R.string.modules_home,
                    defaultIcon = R.drawable.ic_home_default,
                    selectIcon = R.drawable.ic_home_selected
                )
            )
        }
//        if (AppConfig.doctorModulesEnable) {
//            if (GlobalSpEngine.getIsDoctor()) {
//                ServiceFactory.medicineService?.createDoctorHomeFragment()?.run {
//                    fragments.add(this)
//                    navigationItems.add(
//                        MainNavigationItem(
//                            R.string.medical_doctor_client_title_text,
//                            defaultIcon = R.drawable.ic_consultant_default,
//                            selectIcon = R.drawable.ic_consultant_selected
//                        )
//                    )
//                }
//            } else {
//                ServiceFactory.medicineService?.createMedicalAdviceFragment()?.run {
//                    fragments.add(this)
//                    navigationItems.add(
//                        MainNavigationItem(
//                            R.string.modules_medical_advice,
//                            defaultIcon = R.drawable.ic_consultant_default,
//                            selectIcon = R.drawable.ic_consultant_selected
//                        )
//                    )
//                }
//            }
//        }

        if (AppConfig.storeModulesEnable) {
            ServiceFactory.storeService?.createStoreHomeFragment()?.run {
                fragments.add(this)
                navigationItems.add(
                    MainNavigationItem(
                        R.string.modules_store,
                        defaultIcon = R.drawable.ic_store_default,
                        selectIcon = R.drawable.ic_store_selected
                    )
                )
            }
        }

        ServiceFactory.informationService?.createInformationFragment()?.run {
            fragments.add(this)
            navigationItems.add(
                MainNavigationItem(
                    R.string.modules_store,
                    defaultIcon = R.drawable.ic_store_default,
                    selectIcon = R.drawable.ic_store_selected
                )
            )
        }

        ServiceFactory.relativesService?.createRelativesFragment()?.run {
            fragments.add(this)
            navigationItems.add(
                MainNavigationItem(
                    R.string.modules_store,
                    defaultIcon = R.drawable.ic_store_default,
                    selectIcon = R.drawable.ic_store_selected
                )
            )
        }

        ServiceFactory.circleService?.createCircleFragment()?.run {
            fragments.add(this)
            navigationItems.add(
                MainNavigationItem(
                    R.string.modules_circle,
                    defaultIcon = R.drawable.ic_circle_default,
                    selectIcon = R.drawable.ic_circle_selected
                )
            )
        }

        ServiceFactory.mineService?.createMineFragment()?.run {
            fragments.add(this)
            navigationItems.add(
                MainNavigationItem(
                    R.string.modules_mine,
                    defaultIcon = R.drawable.ic_mine_default,
                    selectIcon = R.drawable.ic_mine_selected
                )
            )
        }

        val tabLayout = viewBinding?.tabLayout
        val viewPager = viewBinding?.viewPager
        if (tabLayout != null && viewPager != null) {
            viewPager.adapter = TabFragmentAdapter(this, fragments)
            itemViewBind.clear()
            tabLayout.removeAllTabs()
            TabLayoutMediator(tabLayout, viewPager, true, false) { tab, position ->
                val view = LayoutInflater.from(tab.view.context)
                    .inflate(R.layout.item_main_navigation, tab.view, false)
                val itemViewBinding = DataBindingUtil.bind<ItemMainNavigationBinding>(view)
                view.tag = itemViewBinding

                val item = navigationItems[position]
                itemViewBinding?.tvText?.setText(item.title)
                itemViewBinding?.ivIcon?.setImageResource(item.defaultIcon)

                tab.customView = view
                itemViewBind.add(itemViewBinding)
            }.attach()

            if (fragments.size > 0) {
                viewPager.offscreenPageLimit = fragments.size
                viewPager.currentItem = selectedPage.value
                setTabSelect(selectedPage.value)
            }
        }
    }

    override fun initViewListener() {
        super.initViewListener()

        viewBinding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.run { setTabSelect(tab.position) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.run { setTabUnSelect(tab.position) }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun setTabSelect(position: Int) {
        itemViewBind[position]?.run {
            tvText.setTextColor(ContextProvider.getColor(R.color.color_theme))
            ivIcon.setImageResource(navigationItems[position].selectIcon)
        }
    }

    fun setTabUnSelect(position: Int) {
        itemViewBind[position]?.run {
            tvText.setTextColor(ContextProvider.getColor(R.color.color_gray_95))
            ivIcon.setImageResource(navigationItems[position].defaultIcon)
        }
    }

    private var isExit: Boolean? = false
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true
                ToastUtil.show("再按一次才能退出哦")
                launch {
                    delay(2000)
                    isExit = false
                }
            } else {
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}