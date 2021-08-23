package cn.ycgo.base.medial.adatper

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.storage.bean.DebugInfoBean

/**
 *Author:Kgstt
 *Time: 21-4-21
 */
class DebugListAdapter :BaseQuickAdapter<DebugInfoBean,BaseViewHolder>(R.layout.item_debug_list){
    override fun convert(holder: BaseViewHolder, item: DebugInfoBean) {
        holder.setText(R.id.tv_key,item.key)
        holder.setText(R.id.tv_value,item.value)
    }
}