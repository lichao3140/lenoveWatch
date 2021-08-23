package cn.ycgo.base.medial.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.dpToPxOffset
import cn.ycgo.base.medial.R
import java.util.*

/**
 * 滚轮控件的RecyclerView版本
 */
class WheelRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    //默认参数
    private val DEFAULT_WIDTH: Int = 160.dpToPxOffset()
    private val DEFAULT_ITEM_HEIGHT: Int = 50.dpToPxOffset()
    private val DEFAULT_SELECT_TEXT_COLOR = ContextProvider.getColor(R.color.color_theme)
    private val DEFAULT_UNSELECT_TEXT_COLOR = Color.GRAY
    private val DEFAULT_SELECT_TEXT_SIZE: Int = 14.dpToPxOffset()
    private val DEFAULT_UNSELECT_TEXT_SIZE: Int = 14.dpToPxOffset()
    private val DEFAULT_OFFSET = 1
    private val DEFAULT_DIVIDER_WIDTH = ViewGroup.LayoutParams.MATCH_PARENT
    private val DEFAULT_DIVIDER_HEIGHT: Int = 2
    private val DEFAULT_DIVIVER_COLOR = Color.GRAY
    private var mAdapter: WheelAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private val mDatas //数据
            : MutableList<String?>
    private var mItemHeight //选项高度
            : Int
    private val mOffset //处于中间的item为选中，在头尾需补充 offset个空白view，可显示的item数量=2*offset+1
            : Int
    private val mSelectTextColor //选中item的文本颜色
            : Int
    private val mUnselectTextColor //非选中item的文本颜色
            : Int
    private val mSelectTextSize //选中item的文本大小
            : Float
    private val mUnselectTextSize //非选中item的文本大小
            : Float
    private var mDividerWidth //分割线的宽度
            : Int
    private val mDividerHeight //分割线高度
            : Int
    private val mDividerColor //分割线颜色
            : Int
    private val mPaint //绘制分割线的paint
            : Paint
    private var mOnSelectListener: OnSelectListener? = null
    var selected = 0
        private set

    private fun init() {
        mLayoutManager = LinearLayoutManager(context)
        layoutManager = mLayoutManager
        if (mDividerColor != Color.TRANSPARENT && mDividerHeight != 0 && mDividerWidth != 0) {
            addItemDecoration(DividerItemDecoration())
        }
        mAdapter = WheelAdapter()
        adapter = mAdapter
        addOnScrollListener(OnWheelScrollListener())
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val width: Int
        val height: Int
        val heightSpecSize = MeasureSpec.getSize(heightSpec)
        val heightSpecMode = MeasureSpec.getMode(heightSpec)
        when (heightSpecMode) {
            MeasureSpec.EXACTLY -> {
                height = heightSpecSize
                mItemHeight = height / (mOffset * 2 + 1)
            }
            else -> height = (mOffset * 2 + 1) * mItemHeight
        }
        width = View.getDefaultSize(DEFAULT_WIDTH, widthSpec)
        if (mDividerWidth == DEFAULT_DIVIDER_WIDTH) {
            mDividerWidth = width
        }
        setMeasuredDimension(width, height)
    }

    fun setData(datas: List<String?>?) {
        if (datas == null) {
            return
        }
        mDatas.clear()
        mDatas.addAll(datas)
        mAdapter!!.notifyDataSetChanged()
        setSelect(0)
        scrollTo(0, 0)
    }

    fun setOnSelectListener(listener: OnSelectListener?) {
        mOnSelectListener = listener
    }

    fun setSelect(position: Int) {
        selected = position
        mLayoutManager!!.scrollToPosition(selected)
    }

    private inner class WheelAdapter : Adapter<WheelAdapter.WheelHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WheelHolder {
            val holder = WheelHolder(LayoutInflater.from(context).inflate(R.layout.item_wheel, parent, false))
            holder.name.layoutParams.height = mItemHeight
            return holder
        }

        override fun getItemCount(): Int {
            return if (mDatas.size == 0) 0 else mDatas.size + mOffset * 2
        }

        override fun onBindViewHolder(holder: WheelHolder, position: Int) {
            //头尾填充offset个空白view以使数据能处于中间选中状态
            if (position < mOffset || position > mDatas.size + mOffset - 1) {
                holder.name.text = ""
            } else {
                holder.name.text = mDatas[position - mOffset]
            }
        }

        internal inner class WheelHolder(itemView: View) : ViewHolder(itemView) {
            var name: TextView
            init {
                name = itemView.findViewById<View>(R.id.tv_name) as TextView
            }
        }
    }

    private inner class OnWheelScrollListener : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == SCROLL_STATE_IDLE) {
                //当控件停止滚动时，获取可视范围第一个item的位置，滚动调整控件以使选中的item刚好处于正中间
                val firstVisiblePos = mLayoutManager!!.findFirstVisibleItemPosition()
                if (firstVisiblePos == NO_POSITION) {
                    return
                }
                val rect = Rect()
                mLayoutManager!!.findViewByPosition(firstVisiblePos)!!.getHitRect(rect)
                selected = if (Math.abs(rect.top) > mItemHeight / 2) {
                    smoothScrollBy(0, rect.bottom)
                    firstVisiblePos + 1
                } else {
                    smoothScrollBy(0, rect.top)
                    firstVisiblePos
                }
                if (mOnSelectListener != null) {
                    mOnSelectListener!!.onSelect(selected, mDatas[selected])
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            setSelectedItem()
        }
    }

    private fun setSelectedItem() {
        //获取可视范围的第一个控件的位置
        val firstVisiblePos = mLayoutManager!!.findFirstVisibleItemPosition()
        if (firstVisiblePos == NO_POSITION) {
            return
        }
        val rect = Rect()
        mLayoutManager!!.findViewByPosition(firstVisiblePos)!!.getHitRect(rect)
        //被选中item是否已经滑动超出中间区域
        val overScroll = Math.abs(rect.top) > mItemHeight / 2
        //更新可视范围内所有item的样式
        for (i in 0 until 1 + mOffset * 2) {
            val item: TextView? = if (overScroll) {
                mLayoutManager!!.findViewByPosition(firstVisiblePos + i + 1) as TextView?
            } else {
                mLayoutManager!!.findViewByPosition(firstVisiblePos + i) as TextView?
            }
            if (i == mOffset) {
                item!!.setTextColor(mSelectTextColor)
                item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSelectTextSize)
            } else {
                item!!.setTextColor(mUnselectTextColor)
                item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mUnselectTextSize)
            }
        }
    }

    private inner class DividerItemDecoration : ItemDecoration() {
        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: State) {
            //绘制分割线
            val startX = measuredWidth / 2 - mDividerWidth / 2f
            val topY = mItemHeight * mOffset.toFloat()
            val endX = measuredWidth / 2 + mDividerWidth / 2f
            val bottomY = mItemHeight * (mOffset + 1).toFloat()
            c.drawLine(startX, topY, endX, topY, mPaint)
            c.drawLine(startX, bottomY, endX, bottomY, mPaint)
        }
    }

    interface OnSelectListener {
        fun onSelect(position: Int, data: String?)
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.WheelRecyclerView)
        mItemHeight = ta.getDimension(R.styleable.WheelRecyclerView_itemHeight, DEFAULT_ITEM_HEIGHT.toFloat()).toInt()
        mSelectTextColor = ta.getColor(R.styleable.WheelRecyclerView_selectTextColor, DEFAULT_SELECT_TEXT_COLOR)
        mUnselectTextColor = ta.getColor(R.styleable.WheelRecyclerView_unselectTextColor, DEFAULT_UNSELECT_TEXT_COLOR)
        mSelectTextSize = ta.getDimension(R.styleable.WheelRecyclerView_selectTextSize, DEFAULT_SELECT_TEXT_SIZE.toFloat())
        mUnselectTextSize = ta.getDimension(R.styleable.WheelRecyclerView_unselectTextSize, DEFAULT_UNSELECT_TEXT_SIZE.toFloat())
        mOffset = ta.getInteger(R.styleable.WheelRecyclerView_wheelOffset, DEFAULT_OFFSET)
        mDividerWidth = ta.getDimensionPixelSize(R.styleable.WheelRecyclerView_dividerWidth, DEFAULT_DIVIDER_WIDTH)
        mDividerHeight = ta.getDimensionPixelSize(R.styleable.WheelRecyclerView_dividerHeight, DEFAULT_DIVIDER_HEIGHT)
        mDividerColor = ta.getColor(R.styleable.WheelRecyclerView_dividerColor, DEFAULT_DIVIVER_COLOR)
        ta.recycle()
        mDatas = ArrayList()
        mPaint = Paint()
        mPaint.color = mDividerColor
        mPaint.strokeWidth = mDividerHeight.toFloat()
        init()
    }
}