package cn.ycgo.base.medial.view.dialog.wheel

import android.content.Context
import android.view.*
import android.widget.PopupWindow
import cn.ycgo.base.common.utils.setGone
import cn.ycgo.base.common.utils.setVisible
import cn.ycgo.base.medial.databinding.PopLocationSelectBinding
import cn.ycgo.base.medial.storage.bean.LocationPointBean
import cn.ycgo.base.medial.widget.WheelRecyclerView

/**
 *Author:Kgstt
 *Time: 21-3-2
 */
class WheelLocationSelectPop(val content: Context, layoutInflate: LayoutInflater, val even: (LocationPointBean) -> Unit) : PopupWindow() {
    var selectProvinceIndex = 0
    var selectCityIndex = 0
    var selectCountyIndex = 0
    private var showCounty: Boolean = true
    private val listData = LocationHelper.getAddressLocationBean()
    var viewBinding: PopLocationSelectBinding = PopLocationSelectBinding.inflate(layoutInflate)
    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isOutsideTouchable = true
        isFocusable = true
        contentView = viewBinding.root
        viewBinding.wheelProvince.setData(listData.map { it.getName() })
        viewBinding.wheelCity.setData(getCityNames(0))
        if (showCounty) {
            viewBinding.wheelCounty.setData(getCountyNames(0, 0))
        }
        initViewListener()
    }


    fun isShowCounty(isShow: Boolean): WheelLocationSelectPop {
        showCounty = isShow
        if (isShow) {
            viewBinding.wheelCounty.setVisible()
        } else {
            viewBinding.wheelCounty.setGone()
        }
        return this
    }

    fun initViewListener() {
        viewBinding.tvExit.setOnClickListener { dismiss() }
        viewBinding.tvOk.setOnClickListener {
            val data = LocationPointBean(
                provinceCode = listData[selectProvinceIndex].id,
                provinceName = listData[selectProvinceIndex].getName(),
                cityCode = listData[selectProvinceIndex].child!![selectCityIndex].id,
                cityName = listData[selectProvinceIndex].child!![selectCityIndex].getName(),
                countyCode = if (showCounty) listData[selectProvinceIndex].child!![selectCityIndex].child!![selectCountyIndex].id else 0,
                countyName = if (showCounty) listData[selectProvinceIndex].child!![selectCityIndex].child!![selectCountyIndex].getName() else ""
            )
            even.invoke(data)
            dismiss()
        }

        viewBinding.wheelProvince.setOnSelectListener(object : WheelRecyclerView.OnSelectListener {
            override fun onSelect(position: Int, data: String?) {
                if (selectProvinceIndex != position) {
                    selectProvinceIndex = position
                    viewBinding.wheelCity.setData(getCityNames(position))
                    viewBinding.wheelCounty.setData(getCountyNames(position, 0))
                }
            }
        })
        viewBinding.wheelCity.setOnSelectListener(object : WheelRecyclerView.OnSelectListener {
            override fun onSelect(position: Int, data: String?) {
                if (selectCityIndex != position) {
                    selectCityIndex = position
                    viewBinding.wheelCounty.setData(getCountyNames(selectProvinceIndex, position))
                }
            }
        })
        viewBinding.wheelCounty.setOnSelectListener(object : WheelRecyclerView.OnSelectListener {
            override fun onSelect(position: Int, data: String?) {
                selectCountyIndex = position
            }
        })
    }

    fun getCityNames(provinceIndex: Int): List<String>? {
        return listData[provinceIndex].child?.map { it.getName() }
    }

    fun getCountyNames(provinceIndex: Int, cityIndex: Int): List<String>? {
        return listData[provinceIndex].child?.get(cityIndex)?.child?.map { it.getName() }
    }
}