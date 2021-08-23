package cn.ycgo.base.medial.storage.report

import cn.ycgo.base.common.storage.CommonGlobalValues
import cn.ycgo.base.common.storage.CommonSPEngine
import cn.ycgo.base.common.storage.SPEngine

/**
 *Author:Kgstt
 *Time: 2020/12/21
 */
object GlobalSpEngine : SPEngine() {
    override fun spName(): String {
        return "app_global"
    }

    fun getToken(): String {
        return CommonSPEngine.getToken()
    }

    fun saveToken(token: String?) {
        CommonSPEngine.saveToken(token)
    }

    fun isVisitor(): Boolean {
        return CommonSPEngine.isVisitor()
    }

    fun setIsVisitor(isVisitor: Boolean) {
        CommonSPEngine.setIsVisitor(isVisitor)
    }

    fun getUserId(): Int {
        return get(CommonGlobalValues.USER_ID, -1)
    }

    fun setUserId(id: Int?) {
        save(CommonGlobalValues.USER_ID, id ?: -1)
    }

    fun getUserImName(): String {
        return get(CommonGlobalValues.USER_IM_ID) ?: ""
    }

    fun setUserImName(id: String?) {
        id?.let { save(CommonGlobalValues.USER_IM_ID, it) }
    }

    fun getUserImPwd(): String {
        return get(CommonGlobalValues.USER_IM_PWD) ?: ""
    }

    fun setUserImPwd(id: String?) {
        id?.let { save(CommonGlobalValues.USER_IM_PWD, it) }
    }

    fun getPhone(): String {
        return get(CommonGlobalValues.PHONE) ?: ""
    }

    fun savePhone(num: String) {
        save(CommonGlobalValues.PHONE, num)
    }

    fun getIsDoctor(): Boolean {
        return get(CommonGlobalValues.IS_DOCTOR, false)
    }

    fun saveIsDoctor(isDoctor: Boolean) {
        save(CommonGlobalValues.IS_DOCTOR, isDoctor)
    }

    fun isShowPrivacyRegulations(): Boolean {
        return get("PrivacyRegulations", true)
    }

    fun setShowPrivacyRegulations(isShow: Boolean) {
        save("PrivacyRegulations", isShow)
    }

    fun logoutClean() {
        saveToken("")
        setIsVisitor(true)
        setUserId(0)
        val editor = createSP().edit()
        editor.putString(CommonGlobalValues.PHONE, "")
        editor.putString(CommonGlobalValues.USER_IM_ID, "")
        editor.putString(CommonGlobalValues.USER_IM_PWD, "")
        editor.putBoolean(CommonGlobalValues.IS_DOCTOR, false)
        editor.apply()
    }

}
