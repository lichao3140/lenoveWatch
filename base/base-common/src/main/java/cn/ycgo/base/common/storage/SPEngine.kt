package cn.ycgo.base.common.storage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import cn.ycgo.base.common.utils.ContextProvider
import com.google.gson.Gson
import cn.ycgo.base.common.BuildConfig
import java.lang.Exception
import java.util.HashSet

/**
 * Author:Kgstt
 * Time: 2020/11/24
 * SharedPreferences 其他模块使用需继承此类 参考MedialSPEngine
 */
abstract class SPEngine {
    protected abstract fun spName(): String

    // -- SP读取 --
    protected fun remove(key: String) {
        val editor = createSP().edit()
        editor.remove(key)
        editor.apply()
    }

    protected fun save(bundle: Bundle) {
        val editor = createSP().edit()
        for (str in bundle.keySet()) {
            editor.putString(str, bundle.getString(str))
        }
        editor.apply()
    }

    protected fun save(key: String?, value: String) {
        val editor = createSP().edit()
        editor.putString(key, value)
        editor.apply()
    }

    protected fun save(key: String?, value: Long) {
        val editor = createSP().edit()
        editor.putLong(key, value)
        editor.apply()
    }

    protected fun save(key: String?, value: Float) {
        val editor = createSP().edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    protected fun save(key: String?, value: Boolean) {
        val editor = createSP().edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    protected fun save(key: String?, value: Int) {
        val editor = createSP().edit()
        editor.putInt(key, value)
        editor.apply()
    }

    protected fun saveArray(key: String?, set: Set<String?>?) {
        val editor = createSP().edit()
        editor.putStringSet(key, set)
        editor.apply()
        editor.commit()
    }

    protected fun get(key: String?, default: Int): Int {
        val sp = createSP() ?: return default
        return sp.getInt(key, default)
    }

    protected fun get(key: String?, default: Float): Float {
        val sp = createSP() ?: return default
        return sp.getFloat(key, default)
    }

    protected fun get(key: String?, default: Long): Long {
        val sp = createSP() ?: return default
        return sp.getLong(key, default)
    }

    protected fun get(key: String?, default: String): String? {
        val sp = createSP() ?: return default
        return sp.getString(key, default)
    }

    protected fun get(key: String?, default: Boolean): Boolean {
        val sp = createSP() ?: return default
        return sp.getBoolean(key, default)
    }

    protected fun get(key: String?): String? {
        val sp = createSP() ?: return ""
        return sp.getString(key, "")
    }

    protected fun getArray(key: String?): Set<String>? {
        val set: Set<String> = HashSet()
        val sp = createSP() ?: return null
        return sp.getStringSet(key, set)
    }

    protected fun createSP(): SharedPreferences {
        return ContextProvider.context.getSharedPreferences(
            "${BuildConfig.LIBRARY_PACKAGE_NAME}.${spName()}",
            Context.MODE_PRIVATE
        )
    }

    /***
     * Bean 到 String 的序列化
     */
    protected fun bean2Str(obj: Any?): String {
        return try {
            Gson().toJson(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            "{}"
        }
    }

    /**
     * String 到 Bean 的反序列化
     */
    protected fun <T> str2Bean(string: String?, clazz: Class<T>?): T? {
        return try {
            Gson().fromJson(string, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}