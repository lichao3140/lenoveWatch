package cn.ycgo.base.medial

import cn.ycgo.base.BaseFrameworkApplication
import cn.ycgo.base.medial.api.IMedialApi
import cn.ycgo.base.medial.router.ServiceFactory
import cn.ycgo.base.medial.storage.report.GlobalSpEngine
import cn.ycgo.base.medial.utils.AppUtil
import cn.ycgo.base.medial.utils.ToastUtil
import cn.ycgo.base.network.HttpManager
import cn.ycgo.base.utils.ImmersionBarFactory

/**
 *Author:Kgstt
 *Time: 2020/11/24
 */
open class MedialApplication : BaseFrameworkApplication() {
    override fun onCreate() {
        super.onCreate()
        initGlobalViewStyle()

        //接口统一错误码吗订阅
        //    "403001": "Token被列入黑名单",
        //    "403002": "Token已过期",
        //    "403003": "Token无效",
        //    "403004": "缺少Token",
        //    "403005": "Token过期,刷新失败",
        HttpManager.subscribeErrorCode { code ->
            if (403002 == code) {//Token过期
                AppUtil.onClickSynchronized {
                    ServiceFactory.userService?.refreshToken { response ->
                        if (!response) {
                            ToastUtil.show("登录状态已失效，请重新登录")
                            GlobalSpEngine.setIsVisitor(true)
                            GlobalSpEngine.saveToken("")
                        }
                    }
                }
            } else if (403005 == code) {
                ToastUtil.show("登录状态已失效，请重新登录")
            } else if (403001 == code || 403003 == code || 403004 == code) {//Token 黑名单
                AppUtil.onClickSynchronized {
                    GlobalSpEngine.setIsVisitor(true)
                    GlobalSpEngine.saveToken("")
                    ToastUtil.show("请登录")
                }
            }
        }
        loadLocationCode()
        loadAppConfig()
    }

    private fun loadLocationCode() {
        HttpManager.go(HttpManager.getApi<IMedialApi>().getAddressCodeAsync()) { response ->
            if (response.result) {
                AppConfig.wheelLocationLiveData.postValue(response.data!!)
            }
        }
    }

    private fun loadAppConfig() {
        HttpManager.go(HttpManager.getApi<IMedialApi>().getAppConfigAsync()) { response ->
            if (response.result) {
                AppConfig.appConfigLiveData.postValue(response.data)
            }
        }
    }

    private fun initGlobalViewStyle() {
        ImmersionBarFactory.init(
            R.color.color_system_status_bar,
            R.color.color_system_navigation_bar
        )
    }
}