package cn.ycgo.base.medial.utils

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import cn.ycgo.base.common.utils.ContextProvider

/**
 * @author Brian
 * @date :2020/9/4 11:27
 * @description:
 */

object ToastUtil {
    private var mToast: Toast? = null

    fun show(@StringRes resourcesId: Int) {
        show( ContextProvider.context.getString(resourcesId))
    }

    @SuppressLint("ShowToast")
    fun show( msg: String?) {
        mToast?.cancel()
        mToast = Toast.makeText(ContextProvider.context, msg, Toast.LENGTH_SHORT)
//        mToast?.setGravity(Gravity.CENTER, 0, 0)
        mToast?.duration = Toast.LENGTH_LONG
        mToast?.setText(msg?.trim()?:"")
//        try {
//            val textView:TextView? = (mToast?.view as ViewGroup).getChildAt(0) as TextView
//            textView?.setTextColor(Color.WHITE)
//            textView?.layoutParams?.width=(280).dpToPxOffset()
//            textView?.layoutParams?.height=ViewGroup.LayoutParams.WRAP_CONTENT
//            val padding =(15).dpToPxOffset()
//            textView?.setPadding(padding,padding,padding,padding)
//            textView?.gravity=Gravity.CENTER
//            textView?.setLineSpacing(4.dpToPx(),1f)
//            textView?.background=ContextProvider.getDrawable(R.drawable.toast_bg)
//            mToast?.view?.setBackgroundColor(Color.TRANSPARENT)
//        }catch (ignore:Exception){
//        }
        mToast?.show()
    }

}