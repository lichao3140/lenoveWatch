package cn.ycgo.base.common.widget.recyleView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import cn.ycgo.base.common.utils.dpToPxOffset


/**
 * 封装的RV
 * Created by brian
 * on 2017/5/17.
 */
class LxcRecyclerView : RecyclerView {
    var rownums = 1
    var disableScroll = false

    enum class LayoutType {
        HORIZONTAL //横向
        ,
        VERTICAL //竖向
        ,
        FALLS_HORIZONTAL //横向瀑布流
        ,
        FALLS_VERTICAL //竖向瀑布流
        ,
        GRID_VERTICAL //竖向网格
        ,
        GRID_HORIZONTAL //横向网格
        ,
        FLOW //流式布局
    }

    var type: LayoutType? = null
    private var tempRecyclerDecoration: LxcRecyclerDecoration? = null

    constructor(context: Context?) : super(context!!) {
        setLayoutManagerType(LayoutType.VERTICAL, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        setLayoutManagerType(LayoutType.VERTICAL, 0)
    }

    fun setDivider(dpSize: Float, type: LxcRecyclerDecoration.IntervalType?) {
        if (type == LxcRecyclerDecoration.IntervalType.GRID) {
            addItemDecoration(GridSpaceItemDecoration(rownums, dpSize.toInt()))
        } else {
            tempRecyclerDecoration?.run {
                removeItemDecoration(tempRecyclerDecoration!!)
            }
            tempRecyclerDecoration = LxcRecyclerDecoration(dpSize.dpToPxOffset(), type)
            addItemDecoration(tempRecyclerDecoration!!)
        }
    }

    fun setDivider(dpLeft: Float, dpTop: Float, dpRight: Float, dpBottom: Float, dpFirstItem: Float = 0f, dpEndItem: Float = 0f) {
        tempRecyclerDecoration?.run {
            removeItemDecoration(tempRecyclerDecoration!!)
        }
        tempRecyclerDecoration = LxcRecyclerDecoration(
            dpLeft.toInt().dpToPxOffset(),
            dpTop.toInt().dpToPxOffset(),
            dpRight.toInt().dpToPxOffset(),
            dpBottom.toInt().dpToPxOffset(),
            dpFirstItem.toInt().dpToPxOffset(),
            dpEndItem.toInt().dpToPxOffset()
        ).setLayoutType(type)
        addItemDecoration(tempRecyclerDecoration!!)
    }

    fun setLayoutManagerType(tys: LayoutType?, rowNum: Int = 1) {
        type = tys
        this.rownums = rowNum
        val linearLayoutManager: LinearLayoutManager
        val gridLayoutManager: GridLayoutManager
        when (tys) {
            LayoutType.HORIZONTAL -> {
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager = linearLayoutManager
            }
            LayoutType.VERTICAL -> {
                linearLayoutManager = LinearLayoutManager(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                layoutManager = linearLayoutManager
            }
            LayoutType.FALLS_HORIZONTAL -> layoutManager =
                StaggeredGridLayoutManager(rownums, StaggeredGridLayoutManager.HORIZONTAL)
            LayoutType.FALLS_VERTICAL -> layoutManager =
                StaggeredGridLayoutManager(rownums, StaggeredGridLayoutManager.VERTICAL)
            LayoutType.FLOW -> layoutManager = FlowLayoutManager()
            LayoutType.GRID_HORIZONTAL -> {
                gridLayoutManager = GridLayoutManager(context, rownums)
                gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager = gridLayoutManager
            }
            LayoutType.GRID_VERTICAL -> {
                gridLayoutManager = GridLayoutManager(context, rownums)
                gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
                layoutManager = gridLayoutManager
            }
        }
    }

    fun openPagerSnap() {
        PagerSnapHelper().attachToRecyclerView(this)
    }

    var lis: ImageAutoLoadScrollListener? = null

    /**
     * 开启滚动时Glide不加载图片
     */
    fun openGlideLoadOptimize() {
        lis = ImageAutoLoadScrollListener(context)
        addOnScrollListener(lis!!)
    }

    fun closeGlideLoadOptimize() {
        lis?.let { removeOnScrollListener(it) }
    }

    fun openSlideSnap() {
        LinearSnapHelper().attachToRecyclerView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (type == LayoutType.FLOW || disableScroll) {
            val expandSpec = MeasureSpec.makeMeasureSpec(
                Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST
            )
            super.onMeasure(widthMeasureSpec, expandSpec)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

    }

    /**
     * item显示与消失监听
     */
    fun setOnItemEnterOrExitVisibleListener(listener: OnItemEnterOrExitVisibleHelper.OnScrollStatusListener) {
        val helper = OnItemEnterOrExitVisibleHelper()
        helper.setRecyclerScrollListener(this)
        helper.setOnScrollStatusListener(listener)
    }

    fun getVisibleItemCount(): Int {
        return getLastVisiblePosition() - getFirstVisiblePosition()
    }

    fun getFirstVisiblePosition(): Int {
        try {
            if (layoutManager is LinearLayoutManager) {
                return (layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()
            }
        } catch (e: Exception) {
            return 0
        }
        return 0
    }

    fun getLastVisiblePosition(): Int {
        try {
            if (layoutManager is LinearLayoutManager) {
                return (layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
            }
        } catch (e: Exception) {
            return 0
        }
        return 0
    }

    /**
     * 滚动状态监听
     */
    fun setScrollStatusListener(lis: (ScrollStatus) -> Unit) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val firstCompletelyVisibleItemPosition = layoutManager!!.findFirstCompletelyVisibleItemPosition()
                val lastCompletelyVisibleItemPosition = layoutManager!!.findLastCompletelyVisibleItemPosition()
                if (firstCompletelyVisibleItemPosition == 0) {
//                    Log.i("LXC", "滑动到顶部")
                    lis.invoke(ScrollStatus.TOP)
                } else if (lastCompletelyVisibleItemPosition == layoutManager!!.itemCount - 1) {
//                    Log.i("LXC", "滑动到底部")
                    lis.invoke(ScrollStatus.BOTTOM)
                } else {
//                    Log.i("LXC", "其他")
                    lis.invoke(ScrollStatus.OTHER)
                }
            }
        })
    }

    // 实现渐变效果
    var mPaint: Paint? = null
    private var layerId = 0
    private var linearGradient: LinearGradient? = null
    var topGradualEffectEnable = false
    fun doTopGradualEffect() {
        mPaint = Paint()
        // dst_in 模式，实现底层透明度随上层透明度进行同步显示（即上层为透明时，下层就透明，并不是上层覆盖下层)
        val xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mPaint!!.xfermode = xfermode
        // 透明位置不变，位于Recyclerview偏上位置
        linearGradient = LinearGradient(0.0f, 0.0f, 0.0f, 100.0f, intArrayOf(0, Color.BLACK), null, Shader.TileMode.CLAMP)
        addItemDecoration(object : ItemDecoration() {
            override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: State) {
                super.onDrawOver(canvas, parent, state)
                if (topGradualEffectEnable) {
                    mPaint!!.xfermode = xfermode
                    mPaint!!.shader = linearGradient
                    canvas.drawRect(0.0f, 0.0f, parent.right.toFloat(), 200.0f, mPaint!!)
                    mPaint!!.xfermode = null
                    canvas.restoreToCount(layerId)
                }
            }

            override fun onDraw(c: Canvas, parent: RecyclerView, state: State) {
                super.onDraw(c, parent, state)
                if (topGradualEffectEnable) {
                    // 此处 Paint的参数这里传的null， 在传入 mPaint 时会出现第一次打开黑屏闪现的问题
                    // 注意 saveLayer 不能省也不能移动到onDrawOver方法里
                    layerId = c.saveLayer(0.0f, 0.0f, parent.width.toFloat(), parent.height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
                }
            }

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
                // 该方法作用自行百度
                super.getItemOffsets(outRect, view, parent, state)
            }
        })
    }

    //监听滚动来对图片加载进行判断处理
    class ImageAutoLoadScrollListener(val context: Context) : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                SCROLL_STATE_IDLE ->                     //当屏幕停止滚动，加载图片
                    try {
                        Glide.with(context).resumeRequests()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                SCROLL_STATE_DRAGGING ->                     //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    try {
                        Glide.with(context).pauseRequests()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                SCROLL_STATE_SETTLING ->                     //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    try {
                        Glide.with(context).pauseRequests()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }
    }

}

enum class ScrollStatus {
    TOP, BOTTOM, OTHER
}