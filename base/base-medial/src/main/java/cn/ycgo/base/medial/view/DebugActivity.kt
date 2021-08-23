package cn.ycgo.base.medial.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.DebugConfig
import cn.ycgo.base.common.storage.CommonSPEngine
import cn.ycgo.base.common.storage.enums.ClientType
import cn.ycgo.base.common.widget.recyleView.LxcRecyclerView
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.adatper.DebugListAdapter
import cn.ycgo.base.medial.router.PathConstants
import cn.ycgo.base.medial.router.ServiceFactory
import cn.ycgo.base.medial.storage.bean.DebugInfoBean
import cn.ycgo.base.medial.storage.report.GlobalSpEngine
import cn.ycgo.base.medial.utils.AppUtil
import cn.ycgo.base.medial.utils.ClipboardUtil
import cn.ycgo.base.medial.utils.ToastUtil
import cn.ycgo.base.medial.widget.toolbar.NormalToolBar
import cn.ycgo.base.view.BaseToolBar
import cn.ycgo.base.viewmodel.BaseViewModel
import cn.ycgo.base.medial.databinding.ActivityDebugBinding
import cn.ycgo.base.network.core.Host
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *Author:Kgstt
 *Time: 2021/1/11
 */
@Route(path = PathConstants.ACTIVITY_DEBUG)
class DebugActivity : BasePlusActivity<ActivityDebugBinding, BaseViewModel>() {

    val listAdapter = DebugListAdapter()

    override fun getLayoutResId(): Int {
        return R.layout.activity_debug
    }

    override fun buildToolBar(): BaseToolBar.Builder {
        return NormalToolBar.Builder(this).setTitle("Debug View")
    }


    @SuppressLint("SetTextI18n")
    override fun initView() {
        setActivityBackgroundColor(Color.WHITE)
        super.initView()
        if (!DebugConfig.isDebugMode) {
            finish()
        }
        val mItems = arrayOf("测试环境", "正式环境")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding?.spSelect?.adapter = adapter
        when (DebugConfig.getClientType()) {
            ClientType.DEBUG -> viewBinding?.spSelect?.setSelection(0, true)
            ClientType.RELEASE -> viewBinding?.spSelect?.setSelection(1, true)
        }

        viewBinding?.rvList?.setLayoutManagerType(LxcRecyclerView.LayoutType.VERTICAL)
        viewBinding?.rvList?.adapter = listAdapter

        initData()
    }

    fun initData() {
        val list = ArrayList<DebugInfoBean>()
        list.add(DebugInfoBean("Token", CommonSPEngine.getToken()))
        list.add(DebugInfoBean("UserId", GlobalSpEngine.getUserId().toString()))
        list.add(DebugInfoBean("API-Link", Host.API.toUrl()))
        list.add(DebugInfoBean("H5-Link", Host.H5.toUrl()))
        list.add(DebugInfoBean("是否游客", GlobalSpEngine.isVisitor().toString()))
        list.add(DebugInfoBean("是否医生", GlobalSpEngine.getIsDoctor().toString()))
        list.add(DebugInfoBean("im username", GlobalSpEngine.getUserImName()))
        list.add(DebugInfoBean("version code", AppUtil.getAppVersion(ContextProvider.context).toString()))
        list.add(DebugInfoBean("version name", AppUtil.getVerName() ?: ""))
        listAdapter.setList(list)
    }

    override fun initViewListener() {
        super.initViewListener()
        viewBinding?.spSelect?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ToastUtil.show("APP将会重启")
                launch {
                    delay(300)
                    when (position) {
                        0 -> CommonSPEngine.saveDebugType(ClientType.DEBUG.value)
                        1 -> CommonSPEngine.saveDebugType(ClientType.RELEASE.value)
                    }
                    ServiceFactory.imService?.logoutIm()
                    DebugConfig.setClientType(CommonSPEngine.getDebugType())
                    GlobalSpEngine.logoutClean()
                    val intent: Intent? = packageManager.getLaunchIntentForPackage(ContextProvider.context.packageName)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        listAdapter.setOnItemClickListener { _, _, position ->
            ClipboardUtil.put(this, listAdapter.getItem(position).value)
            ToastUtil.show("参数信息已复制到粘贴板")
        }
    }
}