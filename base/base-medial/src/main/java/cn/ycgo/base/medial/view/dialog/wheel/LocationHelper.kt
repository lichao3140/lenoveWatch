package cn.ycgo.base.medial.view.dialog.wheel

import com.google.gson.Gson
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.AppConfig
import cn.ycgo.base.medial.storage.bean.LocationPointBean
import cn.ycgo.base.medial.storage.bean.ProvinceBean
import org.json.JSONArray
import java.nio.charset.Charset

/**
 *Author:Kgstt
 *Time: 21-3-11
 */
object LocationHelper {
    private fun getAssetsLocationJson(): String? {
        var result = ""
        try {
            val json = ContextProvider.context.assets.open("city.json")
            val buffer = ByteArray(json.available())
            json.read(buffer)
            result = String(buffer, Charset.forName("utf-8"))
            json.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun getAddressLocationBean(): ArrayList<ProvinceBean> {
        val data = ArrayList<ProvinceBean>()
        if (AppConfig.wheelLocationLiveData.value?.isNotEmpty() == true) {
            data.addAll(AppConfig.wheelLocationLiveData.value!!)
        } else {
            val jsArray = JSONArray(getAssetsLocationJson())
            for (index in 0 until jsArray.length()) {
                data.add(Gson().fromJson(jsArray[index].toString(), ProvinceBean::class.java))
            }
            AppConfig.wheelLocationLiveData.postValue(data)
        }
        return data
    }

    fun findCity(countyCode: Int): LocationPointBean {
        val beanList = getAddressLocationBean()
        beanList.forEach { province ->
            province.child?.forEach { city ->
                city.child?.forEach {
                    if (countyCode == it.id) {
                        return LocationPointBean(
                            provinceCode = province.id,
                            provinceName = province.getName(),
                            cityCode = city.id,
                            cityName = city.getName(),
                            countyCode = it.id,
                            countyName = it.getName()
                        )
                    }
                }
            }
        }
        return LocationPointBean(
            provinceCode = 110000,
            provinceName = "北京",
            cityCode = 110100,
            cityName = "北京",
            countyCode = 110101,
            countyName = "东城"
        )
    }

}