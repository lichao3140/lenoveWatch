package cn.ycgo.base.livedata

import androidx.lifecycle.MutableLiveData
import cn.ycgo.base.storage.bean.VMResponse

/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 支持result、msg、object的LiveData
 */
class StatusLiveData<T> : MutableLiveData<VMResponse<T>>()