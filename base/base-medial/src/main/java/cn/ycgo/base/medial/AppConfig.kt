package cn.ycgo.base.medial

import androidx.lifecycle.MutableLiveData
import cn.ycgo.base.medial.storage.bean.AppConfigBean
import cn.ycgo.base.medial.storage.bean.ProvinceBean

/**
 *Author:Kgstt
 *Time: 21-5-14
 */
object AppConfig {
  val wheelLocationLiveData= MutableLiveData<List<ProvinceBean>>()//省市区数据
  val appConfigLiveData = MutableLiveData<AppConfigBean>()//app配置
  var doctorModulesEnable=false//医生模块
  var storeModulesEnable=true//商城模块
}