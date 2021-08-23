package com.lxc.amap.view

import android.view.LayoutInflater
import android.view.View
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.model.Marker
import com.lxc.amap.databinding.LayoutMapInfowindowBinding

/**
 *Author:Kgstt
 *Time: 21-5-11
 */
class AMapInfoWindow(layoutInflater: LayoutInflater) : AMap.InfoWindowAdapter {

    val view = LayoutMapInfowindowBinding.inflate(layoutInflater)

    override fun getInfoContents(p0: Marker?): View {
        return view.root
    }

    override fun getInfoWindow(p0: Marker?): View {
        view.tvName.text=p0?.title
        return view.root
    }


}