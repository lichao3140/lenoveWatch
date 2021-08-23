package cn.ycgo.base.medial.router

import com.alibaba.android.arouter.launcher.ARouter

/**
 *Author:Kgstt
 *Time: 2020/11/24
 */
object ServiceFactory {
    val homeService: IHomeService? = ARouter.getInstance().navigation(IHomeService::class.java)
    val informationService: IInformationService? = ARouter.getInstance().navigation(IInformationService::class.java)
    val piazzaService: IPiazzaService? = ARouter.getInstance().navigation(IPiazzaService::class.java)
    val medicineService: IMedicineService? = ARouter.getInstance().navigation(IMedicineService::class.java)
    val storeService: IStoreService? = ARouter.getInstance().navigation(IStoreService::class.java)
    val shareService: IShareService? = ARouter.getInstance().navigation(IShareService::class.java)
    val adService: IAdService? = ARouter.getInstance().navigation(IAdService::class.java)
    val userService: IUserService? = ARouter.getInstance().navigation(IUserService::class.java)
    val browserService: IBrowserService? = ARouter.getInstance().navigation(IBrowserService::class.java)
    val imService: IImService? = ARouter.getInstance().navigation(IImService::class.java)
    val circleService: ICircleService? = ARouter.getInstance().navigation(ICircleService::class.java)
    val mineService: IMineService? = ARouter.getInstance().navigation(IMineService::class.java)
    val relativesService: IRelativesService? = ARouter.getInstance().navigation(IRelativesService::class.java)
}