package cn.ycgo.base.network

import androidx.lifecycle.LifecycleOwner
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.common.utils.LogUtils
import com.ycgo.base.network.R
import cn.ycgo.base.network.core.Host
import cn.ycgo.base.network.storage.bean.BaseResponse
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *Author:Kgstt
 *Time: 2020/12/22
 */
object HttpManager {

    private const val TAG = "OkHttp"
    private var errorCodeMonitor: ((code: Int) -> Unit?)? = null

    /**
     * 绑定生命周期请求
     */
    fun <T> go(lifecycleOwner: LifecycleOwner, requestService: Deferred<Response<BaseResponse<T>>>, listener: (BaseResponse<T>) -> Unit): Job {
        return GlobalScope.asyncWithLifecycle(lifecycleOwner, Dispatchers.Main) {
            try {
                val response = requestService.await()
                processData(response, listener)
            } catch (e: Exception) {
                LogUtils.e(TAG, "catch Error msg=${e.message}")
                listener.invoke(BaseResponse(-1, false, e.message, null))
            }
        }
    }

    /**
     * 不绑定生命周期请求
     */
    fun <T> go(requestService: Deferred<Response<BaseResponse<T>>>, listener: (BaseResponse<T>) -> Unit): Job {
        return GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = requestService.await()
                processData(response, listener)
            } catch (e: Exception) {
                LogUtils.e(TAG, "catch Error msg=${e.message}")
                listener.invoke(BaseResponse(-1, false, e.message, null))
            }
        }
    }

    /**
     * 没有外层格式限定
     * 不绑定生命周期请求
     */
    fun <T> goDefault(requestService: Deferred<Response<T>>, listener: (T?) -> Unit): Job {
        return GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = requestService.await()
                listener.invoke(response.body())
            } catch (e: Exception) {
                LogUtils.e(TAG, "catch Error msg=${e.message}")
                listener.invoke(null)
            }
        }
    }

    private fun <T> processData(response: Response<BaseResponse<T>>, listener: (BaseResponse<T>) -> Unit) {
        if (response.isSuccessful) {
            val posts = response.body()
            if (posts == null) {
                LogUtils.e(TAG, "code= ${response.code()}  msg=${response.errorBody().toString()}")
                listener.invoke(BaseResponse(response.code(), false, response.errorBody().toString(), null))
            } else {
                if (posts.code == 0) {
                    posts.result = true
                    listener.invoke(posts)
                } else {
                    posts.result = false
                    listener.invoke(posts)
                    errorCodeMonitor?.invoke(posts.code)
                }
            }
        } else {
            var msg = response.errorBody().toString()
            if (response.code() == 500) {
                msg = ContextProvider.getString(R.string.tip_request_server_busy_fail)
            } else if (msg.startsWith("okhttp3.ResponseBody")) {
                msg = ContextProvider.getString(R.string.tip_request_default_error)
            }
            LogUtils.e(TAG, "code= ${response.code()}  msg=${msg}")
            listener.invoke(BaseResponse(response.code(), false, msg, null))
        }
    }

    fun subscribeErrorCode(lis: (code: Int) -> Unit) {
        errorCodeMonitor = lis
    }

    inline fun <reified T : Any> getApi(baseUrl: String = Host.API.toUrl()): T {
        return LxcRetrofitFactory.retrofit(baseUrl)
            .create(T::class.java)
    }

    fun <T> GlobalScope.asyncWithLifecycle(
        lifecycleOwner: LifecycleOwner,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        val deferred = GlobalScope.async(context, start) {
            block()
        }
        lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))
        return deferred
    }
}