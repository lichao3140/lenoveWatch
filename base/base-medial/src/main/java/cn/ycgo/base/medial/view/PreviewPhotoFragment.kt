package cn.ycgo.base.medial.view

import android.net.Uri
import com.github.chrisbanes.photoview.PhotoViewAttacher
import cn.ycgo.base.medial.R
import cn.ycgo.base.medial.databinding.FragmentPreviewPhotoBinding
import cn.ycgo.base.medial.utils.ImageLoadUtil
import cn.ycgo.base.viewmodel.BaseViewModel


class PreviewPhotoFragment(val path: String) : BasePlusFragment<FragmentPreviewPhotoBinding, BaseViewModel>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_preview_photo
    }

    var mAttacher: PhotoViewAttacher? = null

    companion object {
        fun getInstance(path: String): PreviewPhotoFragment {
            return PreviewPhotoFragment(path)
        }
    }

    override fun initView() {
        super.initView()
        val uri = if (path.startsWith("http")) {
            Uri.parse(path)
        } else {
            ImageLoadUtil.getImageContentUri(context!!, path)
        }
        ImageLoadUtil.loadPhotoRetain(context!!, uri!!, viewBinding?.ivPhoto!!,2000)
//        Glide.with(this)
//            .asBitmap()
//            .override(ContextProvider.getDisplayHeight(),ContextProvider.getDisplayHeight())
//            .load(uri)
//            .into(object : SimpleTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    mAttacher = PhotoViewAttacher(viewBinding?.ivPhoto)
//                    viewBinding?.ivPhoto?.setImageBitmap(resource)
//                    mAttacher?.update()
//                }
//            })
    }

}