package cn.ycgo.base.medial.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import cn.ycgo.base.common.utils.dpToPxOffset
import cn.ycgo.base.medial.databinding.DialogBottomBinding
import cn.ycgo.base.utils.getGenericsClass
import cn.ycgo.base.view.BaseBottomDialog
import cn.ycgo.base.viewmodel.BaseViewModel
import cn.ycgo.base.medial.R

/**
 *Author:Kgstt
 *Time: 2020/11/25
 */
@Suppress("unused", "MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
abstract class BasePlusBottomDialog<out T : BasePlusBottomDialog<T, VB, VM>, VB : ViewDataBinding, VM : BaseViewModel> :
    BaseBottomDialog<DialogBottomBinding, VM>() {
    protected var exViewBinding: VB? = null

    private var sheetBehavior: BottomSheetBehavior<*>? = null

    private var enableSheetBehavior = true
    private var isSupportMultiScroller = false
    protected var isTransparentBg = false
    final override fun getLayoutResId(): Int {
        return R.layout.dialog_bottom
    }

    abstract fun getExLayoutResId(): Int

    override fun autoCreateViewModel(): VM {
        return ViewModelProvider(this)[getGenericsClass<VM>(BasePlusBottomDialog::class.java, 2)]
    }

    override fun initView() {
        super.initView()
        viewBinding?.run {
            sheetBehavior = BottomSheetBehavior.from(rootViewBase)
            sheetBehavior?.skipCollapsed = true
            sheetBehavior?.isHideable = true
            rootViewBase.post { sheetBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED) }

            if (!enableSheetBehavior) {
                disableSheetBehavior()
            }

            exViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                getExLayoutResId(),
                rootViewBase,
                false
            )
            exViewBinding?.run {
                rootViewBase.addView(root)

                if (isSupportMultiScroller) {
                    supportMultiScroller(this.root)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(isTransparentBg){
            val lp: WindowManager.LayoutParams? = dialog?.window?.attributes
            lp?.width = ViewGroup.LayoutParams.MATCH_PARENT
            lp?.dimAmount = 0f
            dialog?.window?.attributes=lp
        }
    }

    override fun initViewListener() {
        super.initViewListener()
        viewBinding?.rootView?.setOnClickListener {
            if (!isCancelable || !isCanceledOnTouchOutside) {
                return@setOnClickListener
            }
            dismiss()
        }

        sheetBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss()
                }
            }
        })
    }

    fun disableSheetBehavior(): T {
        sheetBehavior?.peekHeight = 1000.dpToPxOffset()
        sheetBehavior?.isHideable = false
        enableSheetBehavior = false
        viewBinding?.ivSheetElement?.visibility = View.GONE
        return this as T
    }

    fun setSupportMultiScroller() {
        isSupportMultiScroller = true
    }

    private fun supportMultiScroller(root: View) {
        val context = this.context ?: return
        viewBinding?.rootViewBase?.addView(RecyclerView(context), ViewGroup.LayoutParams(0, 0))
        disallowInterceptTouchEvent(root)
    }

    /**
     * 禁止拦截所有的滑动控件的事件
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun disallowInterceptTouchEvent(view: View) {
        if (view !is ViewGroup) {
            return
        }
        val isRecyclerView =
            view is RecyclerView && view.layoutManager is LinearLayoutManager && (view.layoutManager as? LinearLayoutManager)?.orientation == RecyclerView.VERTICAL
        val isListView = view is ListView
        val isViewPager2 = view is ViewPager2 && view.orientation == RecyclerView.VERTICAL
        if (isRecyclerView || isListView || isViewPager2) {
            view.setOnTouchListener { _, _ ->
                viewBinding?.coordinatorLayout?.requestDisallowInterceptTouchEvent(true)
                return@setOnTouchListener false
            }
        } else {
            for (index in 0..view.childCount) {
                disallowInterceptTouchEvent(view.getChildAt(index))
            }
        }
    }
}