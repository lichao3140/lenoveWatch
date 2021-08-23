package cn.ycgo.base.livedata

import androidx.lifecycle.MutableLiveData
import cn.ycgo.base.storage.bean.SimpleVMResponse

/**
 *Author:Kgstt
 *Time: 2020/11/25
 * 只需要result和msg的LiveData
 */
class SimpleStatusLiveData : MutableLiveData<SimpleVMResponse>()

