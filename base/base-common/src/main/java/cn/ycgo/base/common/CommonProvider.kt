package cn.ycgo.base.common

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import cn.ycgo.base.common.utils.ContextProvider

/**
 * Author:Kgstt
 * Time: 2020/11/23
 */
internal class CommonProvider : ContentProvider() {
    private val application = CommonApplication()

    override fun onCreate(): Boolean {
        context?.run {
            ContextProvider.context = applicationContext //ContextProvider优先级最高
            application.onCreate(applicationContext)
        }
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}