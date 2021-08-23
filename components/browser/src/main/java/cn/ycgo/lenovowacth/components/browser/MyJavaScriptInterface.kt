package cn.ycgo.lenovowacth.components.browser

import android.app.Activity


/**
 * @author Brian
 * @date :2020/10/9 18:52
 * @description:
 */
class MyJavaScriptInterface(val activity:Activity) {


    var listener: ((Boolean) -> Unit?)? = null

    fun loadingListener(listener: (Boolean) -> Unit): MyJavaScriptInterface {
        this.listener = listener
        return this
    }
}