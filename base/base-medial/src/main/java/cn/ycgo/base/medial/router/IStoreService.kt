package cn.ycgo.base.medial.router

import com.alibaba.android.arouter.facade.template.IProvider
import cn.ycgo.base.storage.bean.SimpleVMResponse
import cn.ycgo.base.view.BaseFragment

/**
 *Author:Kgstt
 *Time: 2021/1/21
 */
interface IStoreService : IProvider {
    fun createStoreHomeFragment(): BaseFragment<*, *>
    fun startSelectDynamicGoods(selectGoodsList: ArrayList<Int>?, selectRentingList: ArrayList<Int>?, selectPricingList: ArrayList<Int>?, event: (ArrayList<Int>, ArrayList<Int>, ArrayList<Int>) -> Unit)
    fun createSearchPricingFragment(): BaseFragment<*, *>
    fun changeSearchPricingFragmentKeyword(fragment: BaseFragment<*, *>, keyword: String)
    fun getGoodsCartCount(call: (Int) -> Unit)
    fun addGoodsCart(goodsId: Int, skuId: Int, count: Int, call: (SimpleVMResponse) -> Unit)
}