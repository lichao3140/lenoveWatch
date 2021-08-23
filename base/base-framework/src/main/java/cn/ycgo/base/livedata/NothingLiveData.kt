package cn.ycgo.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 不需要任何值的LiveData
 */
open class NothingLiveData : MutableLiveData<Unit>() {
    @Deprecated("replace with postValue()", ReplaceWith("postValue()"), DeprecationLevel.HIDDEN)
    override fun postValue(value: Unit?) {
        super.postValue(value)
    }

    @Deprecated("replace with setValue()", ReplaceWith("setValue()"), DeprecationLevel.HIDDEN)
    override fun setValue(value: Unit?) {
        super.setValue(value)
    }

    fun postValue() {
        super.postValue(Unit)
    }

    fun setValue() {
        super.setValue(Unit)
    }
}

