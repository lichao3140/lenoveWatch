package cn.ycgo.base.medial.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import cn.ycgo.base.common.utils.ContextProvider
import cn.ycgo.base.medial.R
import java.io.File


/**
 * Author:Kgstt
 * Time: 2020/11/23
 * Glide4.x的加载图片引擎实现,单例模式
 * Glide4.x的缓存机制更加智能，已经达到无需配置的境界。如果使用Glide3.x，需要考虑缓存机制。
 */
object ImageLoadUtil {

    /**
     * 加载图片到ImageView
     *
     * @param context   上下文
     * @param uri       图片路径Uri
     * @param imageView 加载到的ImageView
     */
    //安卓10推荐uri，并且path的方式不再可用
    fun loadDefaultImage(context: Context, uri: Uri, imageView: ImageView, overrideW: Int = -1, overrideH: Int = -1, @DrawableRes place: Int? = 0) {
        val glide = Glide.with(context)
            .load(uri)
            .override(overrideW, overrideH)
        if (place != 0) {
            place?.let {
                glide.error(it)
                glide.placeholder(it)
            }
        }
        glide.into(imageView)
    }

    /**
     * 加载图片到ImageView
     *
     * @param context   上下文
     * @param uri       图片路径Uri
     * @param imageView 加载到的ImageView
     */
    //安卓10推荐uri，并且path的方式不再可用
    fun loadPhoto(context: Context, uri: Uri, imageView: ImageView, @DrawableRes place: Int = R.drawable.ic_image_place) {
        imageView.setBackgroundColor(ContextProvider.getColor(R.color.color_gray_F3F4F6))
        Glide.with(context)
            .load(uri)
            .error(place)
            .placeholder(place)
            .autoClone()
            .into(imageView)
    }

    /**
     * 加载图片到ImageView
     *
     * @param context   上下文
     * @param uri       图片路径Uri
     * @param imageView 加载到的ImageView
     */
    //安卓10推荐uri，并且path的方式不再可用
    fun loadPhotoRetain(context: Context, uri: Uri, imageView: ImageView, pxSize:Int,@DrawableRes place: Int = R.drawable.ic_image_place) {
        Glide.with(context)
            .load(uri)
            .override(pxSize)
            .error(place)
            .placeholder(place)
            .autoClone()
            .into(imageView)
    }

    fun loadAvatarPhoto(context: Context, uri: Uri, imageView: ImageView, @DrawableRes place: Int = R.drawable.ic_users_default) {
        Glide.with(context)
            .load(uri)
            .override(200, 200)
            .centerCrop()
            .error(place)
            .placeholder(place)
            .into(imageView)
    }

    @SuppressLint("CheckResult")
    fun loadPhotoToBitmap(context: Context, uri: Uri, overrideW: Int = -1, overrideH: Int = -1, isCrap:Boolean=false, @DrawableRes place: Int? = 0, bitmap: (Bitmap) -> Unit) {
        val glide = Glide.with(context)
            .asBitmap()
            .load(uri)
            .override(overrideW, overrideH)
        if (place != 0) {
            place?.let {
                glide.error(it)
                glide.placeholder(it)
            }
        }
        if(isCrap){
            glide.centerCrop()
        }
        glide.into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.invoke(resource)
            }
        })
    }

    /**
     * 安卓10加载本机图片
     */
    fun getImageContentUri(context: Context, path: String): Uri? {
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ", arrayOf(path), null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (File(path).exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, path)
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }
}
