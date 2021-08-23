package cn.ycgo.base.medial.view.dialog.wheel

import androidx.lifecycle.Observer
import cn.ycgo.base.common.utils.setGone
import cn.ycgo.base.common.utils.setVisible
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.DialogLocationSelectBinding
import cn.ycgo.base.medial.storage.bean.LocationPointBean
import cn.ycgo.base.medial.viewmodel.WheelLocationSelectVM
import cn.ycgo.base.medial.widget.WheelRecyclerView
import cn.ycgo.base.view.BaseBottomDialog

/**
 *Author:Kgstt
 *Time: 21-3-2
 */
class WheelLocationSelectDialog(val even: (LocationPointBean) -> Unit) : BaseBottomDialog<DialogLocationSelectBinding, WheelLocationSelectVM>() {

    private var showCounty: Boolean = true

    override fun getLayoutResId(): Int {
        return R.layout.dialog_location_select
    }

    fun isShowCounty(isShow: Boolean): WheelLocationSelectDialog {
        showCounty = isShow
        return this
    }

    override fun onResume() {
        super.onResume()
        if (showCounty) {
            viewBinding?.wheelCounty?.setVisible()
        } else {
            viewBinding?.wheelCounty?.setGone()
        }
    }

    override fun initViewObserver() {
        super.initViewObserver()
        viewModel?.locationLiveData?.observe(this, Observer { arrayList ->
            viewBinding?.wheelProvince?.setData(arrayList.map { it.getName() })
            viewBinding?.wheelCity?.setData(viewModel?.getCityNames(0))
            if (showCounty) {
                viewBinding?.wheelCounty?.setData(viewModel?.getCountyNames(0, 0))
            }
        })
    }

    override fun initViewListener() {
        super.initViewListener()
        viewBinding?.tvExit?.setOnClickListener { dismiss() }
        viewBinding?.tvOk?.setOnClickListener {
            viewModel?.let {
                it.locationLiveData.value?.run {
                    val data = LocationPointBean(
                        provinceCode = get(it.selectProvinceIndex).id,
                        provinceName = get(it.selectProvinceIndex).getName(),
                        cityCode = get(it.selectProvinceIndex).child!![it.selectCityIndex].id,
                        cityName = get(it.selectProvinceIndex).child!![it.selectCityIndex].getName(),
                        countyCode = if (showCounty) get(it.selectProvinceIndex).child!![it.selectCityIndex].child!![it.selectCountyIndex].id else 0,
                        countyName = if (showCounty) get(it.selectProvinceIndex).child!![it.selectCityIndex].child!![it.selectCountyIndex].getName() else ""
                    )
                    even.invoke(data)
                }
            }
            dismiss()
        }

        viewBinding?.wheelProvince?.setOnSelectListener(object : WheelRecyclerView.OnSelectListener {
            override fun onSelect(position: Int, data: String?) {
                if (viewModel?.selectProvinceIndex != position) {
                    viewModel?.selectProvinceIndex = position
                    viewBinding?.wheelCity?.setData(viewModel?.getCityNames(position))
                    if (showCounty) {
                        viewBinding?.wheelCounty?.setData(viewModel?.getCountyNames(position, 0))
                    }
                }
            }
        })
        viewBinding?.wheelCity?.setOnSelectListener(object : WheelRecyclerView.OnSelectListener {
            override fun onSelect(position: Int, data: String?) {
                if (viewModel?.selectCityIndex != position) {
                    viewModel?.selectCityIndex = position
                    if (showCounty) {
                        viewBinding?.wheelCounty?.setData(viewModel?.getCountyNames(viewModel?.selectProvinceIndex!!, position))
                    }
                }
            }
        })
        viewBinding?.wheelCounty?.setOnSelectListener(object : WheelRecyclerView.OnSelectListener {
            override fun onSelect(position: Int, data: String?) {
                viewModel?.selectCountyIndex = position
            }
        })
    }

}