package cn.ycgo.base.common.utils

import android.annotation.SuppressLint
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ycgo.base.common.widget.recyleView.item.BaseItem
import cn.ycgo.base.common.widget.recyleView.item.BaseItemAdapter
import cn.ycgo.base.common.widget.recyleView.item.RecycleViewDivider

/**
 * ```
 * author: Kgstt
 * time  : 1/15/21 2:17 PM
 * ```
 */
@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
class RvAdapterFactory<T : BaseItem<*>>(private val mView: RecyclerView) {
    private var mAdapter: BaseItemAdapter<T>? = null
    private var spanCount = 1
    private var orientation = LinearLayout.VERTICAL
    fun setSpanCount(spanCount: Int): RvAdapterFactory<*> {
        this.spanCount = spanCount
        return this
    }

    fun setHorizontal(): RvAdapterFactory<*> {
        orientation = LinearLayout.HORIZONTAL
        return this
    }

    fun addItemDecoration(orientation: Int, drawableId: Int): RvAdapterFactory<*> {
        mView.addItemDecoration(RecycleViewDivider(mView.context, orientation, drawableId))
        return this
    }

    @SuppressLint("WrongConstant")
    fun  create(list: List< T>): BaseItemAdapter<T> {
        mAdapter = BaseItemAdapter()
        val layoutManager = GridLayoutManager(mView.context, spanCount)
        layoutManager.orientation = orientation
        mView.layoutManager = layoutManager
        mAdapter!!.items = list
        mView.adapter = mAdapter
        return mAdapter as BaseItemAdapter<T>
    }

    companion object {
        fun go(view: RecyclerView): RvAdapterFactory<BaseItem<*>> {
            return RvAdapterFactory<BaseItem<*>>(view)
        }
    }

}