package cn.ycgo.base.common.storage

import cn.ycgo.base.common.storage.enums.ClientType


/**
 *Author:Kgstt
 *Time: 2020/11/24
 * 最Base的公共数据的SP
 */
object CommonSPEngine : SPEngine() {

    override fun spName(): String {
        return "base"
    }

    fun getToken(): String {
        return get(CommonGlobalValues.TOKEN, "") ?: ""

    }

    fun saveToken(token: String?) {
        save(CommonGlobalValues.TOKEN, token ?: "")
    }

    fun isVisitor(): Boolean {
        return get(CommonGlobalValues.IS_VISITOR, true)
    }

    fun setIsVisitor(isVisitor: Boolean) {
        save(CommonGlobalValues.IS_VISITOR, isVisitor)
    }


    fun getDebugType(): ClientType {
        return ClientType.fromValue(get(CommonGlobalValues.DEBUG_TYPE, ClientType.DEBUG.value)) ?: ClientType.DEBUG
    }

    fun saveDebugType(type: Int) {
        save(CommonGlobalValues.DEBUG_TYPE, type)
    }

}