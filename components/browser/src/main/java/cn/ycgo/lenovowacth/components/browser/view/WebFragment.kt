package cn.ycgo.lenovowacth.components.browser.view


import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import cn.ycgo.base.common.storage.CommonSPEngine
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.view.BasePlusFragment
import cn.ycgo.base.viewmodel.BaseViewModel
import cn.ycgo.lenovowacth.components.browser.MyJavaScriptInterface
import com.ycgo.components.browser.R
import com.ycgo.components.browser.databinding.FragmentWebBinding
import me.jessyan.autosize.internal.CancelAdapt

/**
 * @author Brian
 * @date :2020/10/9 18:00
 * @description:
 * 此页面忽略autoSize适配框架
 */
class WebFragment(val link: String) : BasePlusFragment<FragmentWebBinding, BaseViewModel>(), CancelAdapt {
    override fun getLayoutResId() = R.layout.fragment_web
    private var mAgentWeb: AgentWeb? = null
    private lateinit var mWebChromeClient: WebChromeClient

    var listener: ((String) -> Unit?)? = null

    companion object {
        fun newInstance(link: String): WebFragment {
            return WebFragment(link)
        }
    }

    @SuppressLint("JavascriptInterface")
    override fun initView() {
        super.initView()
        mWebChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                listener?.invoke(title)
            }
        }
        viewBinding?.vWebLayout?.let { frameLayout ->
            mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(
                    frameLayout,
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                )
                .useDefaultIndicator(ContextProvider.getColor(R.color.color_theme))
                .setWebChromeClient(mWebChromeClient)
                .additionalHttpHeader("Authorization", "Bearer ${CommonSPEngine.getToken()}")
                .setWebViewClient(object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        url: String
                    ): Boolean {
                        return false
                    }
                })
                .createAgentWeb()
                .go(link)

            mAgentWeb?.agentWebSettings?.webSettings?.apply {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
//                                }
                loadWithOverviewMode = true
                useWideViewPort = true
                cacheMode = WebSettings.LOAD_DEFAULT
                javaScriptEnabled = true
                domStorageEnabled = true
                javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
                loadsImagesAutomatically = true //支持自动加载图片
                defaultTextEncodingName = "utf-8" //设置编码格式
            }
            mAgentWeb?.webCreator?.webView?.addJavascriptInterface(
                MyJavaScriptInterface(activity!!).loadingListener {
                    if (it) {
                        showLoadingDialog()
                    } else {
                        dismissLoadingDialog()
                    }
                },
                "handlers"
            )
        }
    }

    fun setTitleListener(listener: (String) -> Unit) {
        this.listener = listener
    }


    fun reload() {
        mAgentWeb?.webCreator?.webView?.reload()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb?.webLifeCycle?.onDestroy()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    public fun doBack(): Boolean {
        return mAgentWeb?.back() ?: false
    }

}