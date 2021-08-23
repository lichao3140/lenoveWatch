package cn.ycgo.base.medial.adatper

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlin.collections.ArrayList

/**
 * Created by Brian on 2017/5/11.
 * ViewPager嵌套View的adapter
 */
class ControlViewPagerAdapter(list: ArrayList<out View>?) : PagerAdapter() {
    private val list: List<View>?
    private var listTitle: List<String>? = null

    var listener: ((Int) -> Unit?)? = null


    override fun getCount(): Int {
        return list?.size ?: 0
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(list!![position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val v = list!![position]
        v.setOnClickListener {
            listener?.invoke(position)
        }
        container.addView(v)

        return list[position]
    }

    fun setTitle(titles: ArrayList<String>) {
        listTitle = titles
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (listTitle?.isNotEmpty() == true) {
            return listTitle!![position]
        }
        return "●"
    }

    fun setOnItemClickListener(lis: (Int) -> Unit) {
        listener = lis
    }

    init {
        this.list = list
    }
}