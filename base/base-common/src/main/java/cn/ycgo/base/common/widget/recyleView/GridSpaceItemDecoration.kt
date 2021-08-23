package cn.ycgo.base.common.widget.recyleView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import cn.ycgo.base.common.utils.dpToPxOffset


/**
 * 作者：HK
 * 日期：2019/5/5
 * 描述：宫格布局的间隔处理
 *
 * 横向：中间有等距间隔（左右两边与屏幕边沿的间隔另行处理）；
 * 纵向：第二行往下开始，有顶部的间隔（且每行间隔都相等）
 */

class GridSpaceItemDecoration(private val spanCount: Int, dividerWidthDp: Int) : ItemDecoration() {
    private val dividerWidth: Int = dividerWidthDp.dpToPxOffset()

    override fun getItemOffsets(outRect: Rect, child: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, child, parent, state)
        val pos = parent.getChildAdapterPosition(child)
        // 计算这个child 处于第几列
        val column = pos % spanCount
        outRect.left = column * dividerWidth / spanCount
        outRect.right = dividerWidth - (column + 1) * dividerWidth / spanCount
        if (pos >= spanCount) outRect.top = dividerWidth
    }
}