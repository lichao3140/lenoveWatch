package cn.ycgo.base.medial.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.ycgo.base.medial.storage.bean.ProvinceBean
import cn.ycgo.base.medial.view.dialog.wheel.LocationHelper
import cn.ycgo.base.viewmodel.BaseViewModel

/**
 *Author:Kgstt
 *Time: 21-3-3
 */
class WheelLocationSelectVM : BaseViewModel() {

    val locationLiveData = MutableLiveData<List<ProvinceBean>>()
    var selectProvinceIndex = 0
    var selectCityIndex = 0
    var selectCountyIndex = 0

    override fun initData() {
        super.initData()
        loadLocation()
    }

    private fun loadLocation() {
        locationLiveData.postValue(LocationHelper.getAddressLocationBean())
    }

    fun getCityNames(provinceIndex: Int): List<String>? {
        return locationLiveData.value?.get(provinceIndex)?.child?.map { it.getName() }
    }

    fun getCountyNames(provinceIndex: Int, cityIndex: Int): List<String>? {
        return locationLiveData.value?.get(provinceIndex)?.child?.get(cityIndex)?.child?.map { it.getName() }
    }

}