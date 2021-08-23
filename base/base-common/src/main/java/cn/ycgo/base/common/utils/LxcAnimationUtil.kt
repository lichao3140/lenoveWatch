package cn.ycgo.base.common.utils

import android.animation.*
import android.graphics.Path
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.*
import java.util.*

/**
 * 属性动画工具类
 * Created by brian
 * on 2018/5/17.
 */
class LxcAnimationUtil(vararg view: Any) {

    enum class AnimType {
        ALPHA,  //透明
        SCALE_X,  //缩放
        SCALE_Y,  //缩放
        ROTATION,  //旋转
        ROTATION_X,  //旋转X轴
        ROTATION_Y,  //旋转Y轴
        TRANSLATION_X,  //平移
        TRANSLATION_Y,  //平移
        PIVOT_X,  //围绕view旋转
        PIVOT_Y //围绕view旋转
    }

    enum class ZoomType {
        EXTEND //伸展
        ,
        REDUCE //缩小
    }

    enum class CircleType {
        TOP, BOTTOM, CENTER, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    enum class InterpolatorType {
        开始慢_中间加速 // 加速，开始时慢中间加速
        ,
        开始快_然后减速 // 减速，开始时快然后减速
        ,
        中间快 //先加速后减速，开始结束时慢，中间加速
        ,
        反向 // 反向 ，先向相反方向改变一段再加速播放
        ,
        反向加超越 //反向加超越，先向相反方向改变，再加速播放，会超出目的值然后缓 慢移动至目的值
        ,
        快到终点跳跃 //　 跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77 ，70，80，90，100
        ,
        循环一定次数 // 循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 * mCycles * Math.PI * input)
        ,
        均匀改变 //线性，线性均匀改变
        ,
        超越 // 超越，最后超出目的值然后缓慢改变到目的值
        ,
        反弹 // 反弹
    }

    var mObjectAnimator: ObjectAnimator? = null
    var list: MutableList<PropertyValuesHolder>?
    var tags: List<Any>
    var dus = 800
    var mListener: Animator.AnimatorListener? = null
    var mode = ObjectAnimator.RESTART
    var count = 0
    var delayStartTime = 0
    var mTimeInterpolator: TimeInterpolator? = null


    init {
        list = ArrayList()
        tags = view.toList()
    }

    /**
     * 设置动画插入器
     *
     * @param type 动画类型
     * @return
     */
    fun setInterpolator(type: InterpolatorType?, count: Float): LxcAnimationUtil {
        when (type) {
            InterpolatorType.开始慢_中间加速 -> mTimeInterpolator = OvershootInterpolator()
            InterpolatorType.开始快_然后减速 -> mTimeInterpolator = DecelerateInterpolator()
            InterpolatorType.中间快 -> mTimeInterpolator = AccelerateDecelerateInterpolator()
            InterpolatorType.反向 -> mTimeInterpolator = AnticipateInterpolator()
            InterpolatorType.反向加超越 -> mTimeInterpolator = AnticipateOvershootInterpolator()
            InterpolatorType.快到终点跳跃 -> mTimeInterpolator = BounceInterpolator()
            InterpolatorType.循环一定次数 -> mTimeInterpolator = CycleInterpolator(count)
            InterpolatorType.均匀改变 -> mTimeInterpolator = LinearInterpolator()
            InterpolatorType.超越 -> mTimeInterpolator = OvershootInterpolator()
            InterpolatorType.反弹 -> mTimeInterpolator = BounceInterpolator()
        }
        return this
    }

    /**
     * 添加动画
     *
     * @param value
     * @return
     */
    fun addAnimation(value: PropertyValuesHolder): LxcAnimationUtil {
        list!!.add(value)
        return this
    }

    /**
     * 添加动画
     *
     * @param type  动画属性类型
     * @param value form--to 节点
     * @return
     */
    fun addAnimation(type: AnimType?, vararg value: Float): LxcAnimationUtil {
        var types: String? = null
        when (type) {
            AnimType.ALPHA -> types = "alpha"
            AnimType.SCALE_X -> types = "scaleX"
            AnimType.SCALE_Y -> types = "scaleY"
            AnimType.ROTATION -> types = "rotation"
            AnimType.ROTATION_X -> types = "rotationX"
            AnimType.ROTATION_Y -> types = "rotationY"
            AnimType.TRANSLATION_X -> types = "translationX"
            AnimType.TRANSLATION_Y -> types = "translationY"
            AnimType.PIVOT_X -> types = "pivotX"
            AnimType.PIVOT_Y -> types = "pivotY"
        }
        list!!.add(PropertyValuesHolder.ofFloat(types, *value))
        return this
    }

    /**
     * 动画播放时间
     *
     * @param duss
     * @return
     */
    fun setDuration(duss: Int): LxcAnimationUtil {
        dus = duss
        return this
    }

    /**
     * 动画模式
     *
     * @param ObjectAnimatorValus
     * @return
     */
    fun setRepeatMode(ObjectAnimatorValus: Int): LxcAnimationUtil {
        mode = ObjectAnimatorValus
        return this
    }

    /**
     * 动画重复
     *
     * @param ObjectAnimatorValus
     * @return
     */
    fun setRepeatCount(ObjectAnimatorValus: Int): LxcAnimationUtil {
        count = ObjectAnimatorValus
        return this
    }

    /**
     * 动画监听
     *
     * @param listen
     * @return
     */
    fun setListener(listen: Animator.AnimatorListener?): LxcAnimationUtil {
        mListener = listen
        return this
    }

    /**
     * 开启动画(所有设置必须在start前完成)
     */
    fun start() {
        if (list == null || list!!.size == 0) {
            return
        }
        for (i in tags.indices) {
            mObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(tags[i], *list!!.toTypedArray())
            mObjectAnimator!!.repeatMode = mode
            if (count != 0) {
                mObjectAnimator!!.repeatCount = count
            }
            if (mListener != null) {
                mObjectAnimator!!.addListener(mListener)
            }
            if (mTimeInterpolator != null) {
                mObjectAnimator!!.interpolator = mTimeInterpolator
            }
            mObjectAnimator!!.duration = dus.toLong()
            mObjectAnimator!!.startDelay = delayStartTime.toLong()
            mObjectAnimator!!.start()
        }
    }

    /**
     * 动画延迟执行时间
     *
     * @param time
     * @return
     */
    fun setDelayStartTime(time: Int): LxcAnimationUtil {
        delayStartTime = time
        return this
    }

    fun getmObjectAnimator(): ObjectAnimator? {
        return mObjectAnimator
    }

    /**
     * 取消,view留在取消点
     */
    fun cacel() {
        if (mObjectAnimator != null) {
            mObjectAnimator!!.cancel()
        }
    }

    /**
     * 暂停动画
     */
    fun pause() {
        if (mObjectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mObjectAnimator!!.pause()
            }
        }
    }

    /**
     * 暂停后重新播放
     */
    fun resume() {
        if (mObjectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mObjectAnimator!!.resume()
            }
        }
    }

    /**
     * 结束,view停留在最终点
     */
    fun end() {
        if (mObjectAnimator != null) {
            mObjectAnimator!!.end()
        }
    }

    /**
     * 颜色渐变
     *
     * @param duration 动画时间
     * @param value    动画节点
     */
    fun startGradientColors(duration: Int, vararg value: Int) {
        startGradientColors(false, duration, *value)
    }

    /**
     * 颜色渐变
     *
     * @param isRepetition 是否重复
     * @param duration     动画时间
     * @param value        动画节点
     */
    fun startGradientColors(isRepetition: Boolean, duration: Int, vararg value: Int) {
        for (i in tags.indices) {
            val colorAnim = ObjectAnimator.ofInt(tags[i] as View, "backgroundColor", *value)
            colorAnim.duration = duration.toLong()
            colorAnim.setEvaluator(ArgbEvaluator())
            if (isRepetition) {
                colorAnim.repeatCount = ValueAnimator.INFINITE
                colorAnim.repeatMode = ValueAnimator.REVERSE
            }
            colorAnim.start()
        }
    }

    /**
     * 圆心动画(必须View加载完才能调用--即view有宽高时才有效)--->(重写BaseActivity的onViewLoadFinish()方法再作执行)
     *
     * @param zoomType 扩展还是缩小
     * @param duration 动画时间
     * @param centerX  X中心
     * @param centerY  Y中心
     * @param baseXy   从baseXy开始扩大或者缩小到baseXy
     */
    fun startCircleAnimation(
        zoomType: ZoomType,
        duration: Int,
        delayStartTime: Int,
        centerX: Int,
        centerY: Int,
        baseXy: Int,
        listener: Animator.AnimatorListener?
    ) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                var anim: Animator? = null
                for (i in tags.indices) {
                    anim = if (zoomType == ZoomType.EXTEND) {
                        ViewAnimationUtils.createCircularReveal(
                            tags[i] as View,
                            centerX,
                            centerY,
                            baseXy.toFloat(),
                            Math.max((tags[i] as View).width, (tags[i] as View).height).toFloat()
                        )
                    } else {
                        ViewAnimationUtils.createCircularReveal(
                            tags[i] as View,
                            centerX,
                            centerY,
                            Math.max((tags[i] as View).width, (tags[i] as View).height).toFloat(),
                            baseXy.toFloat()
                        )
                    }
                    anim.duration = duration.toLong()
                    anim.startDelay = delayStartTime.toLong()
                    if (listener != null) {
                        anim.addListener(listener)
                    }
                    anim.start()
                }
            } else {
                faliCircle(zoomType, duration, delayStartTime, centerX, centerY, listener)
            }
        } catch (e: Exception) {
            faliCircle(zoomType, duration, delayStartTime, centerX, centerY, listener)
        }
    }

    fun faliCircle(
        zoomType: ZoomType,
        duration: Int,
        delayStartTime: Int,
        centerX: Int,
        centerY: Int,
        listener: Animator.AnimatorListener?
    ) {
        for (i in tags.indices) {
            val w = (tags[i] as View).width / 2
            val h = (tags[i] as View).height / 2
            if (zoomType == ZoomType.EXTEND) {
                addAnimation(AnimType.SCALE_X, 0f, 1f)
                    .addAnimation(AnimType.SCALE_Y, 0f, 1f)
                    .addAnimation(AnimType.TRANSLATION_X, (centerX - w).toFloat(), 0f)
                    .addAnimation(AnimType.TRANSLATION_Y, (centerY - h).toFloat(), 0f)
                    .addAnimation(AnimType.ALPHA, 0f, 1f)
                    .setDelayStartTime(delayStartTime)
                    .setDuration(duration)
            } else {
                addAnimation(AnimType.SCALE_X, 1f, 0f)
                    .addAnimation(AnimType.SCALE_Y, 1f, 0f)
                    .addAnimation(AnimType.TRANSLATION_X, 0f, (centerX - w).toFloat())
                    .addAnimation(AnimType.TRANSLATION_Y, 0f, (centerY - h).toFloat())
                    .addAnimation(AnimType.ALPHA, 1f, 0f)
                    .setDelayStartTime(delayStartTime)
                    .setDuration(duration)
            }
            listener?.let { setListener(it) }
            start()
        }
    }

    /**
     * 圆心动画(必须View加载完(BaseActivity的onViewLoadFinish()方法重写)才能调用)(即view有宽高时才有效)
     *
     * @param zoomType     扩展还是缩小
     * @param locationType 开始位置
     * @param duration     动画时间
     * @param baseXY       从baseXy开始扩大或者缩小到baseXy
     */
    fun startCircleAnimation(
        zoomType: ZoomType,
        locationType: CircleType?,
        duration: Int,
        baseXY: Int,
        listener: Animator.AnimatorListener?
    ) {
        startCircleAnimation(zoomType, locationType, duration, 0, baseXY, listener)
    }

    /**
     * 圆心动画(必须View加载完(BaseActivity的onViewLoadFinish()方法重写)才能调用)(即view有宽高时才有效)
     *
     * @param zoomType     扩展还是缩小
     * @param locationType 开始位置
     * @param duration     动画时间
     * @param baseXy       从baseXy开始扩大或者缩小到baseXy
     */
    fun startCircleAnimation(
        zoomType: ZoomType,
        locationType: CircleType?,
        duration: Int,
        delayStartTime: Int,
        baseXy: Int,
        listener: Animator.AnimatorListener?
    ) {
        for (i in tags.indices) {
            (tags[i] as View).measure(0, 0)
            when (locationType) {
                CircleType.BOTTOM -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    (tags[i] as View).width / 2,
                    (tags[i] as View).height - 10,
                    baseXy,
                    listener
                )
                CircleType.TOP -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    (tags[i] as View).width / 2,
                    10,
                    baseXy,
                    listener
                )
                CircleType.CENTER -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    (tags[i] as View).width / 2,
                    (tags[i] as View).height / 2,
                    baseXy,
                    listener
                )
                CircleType.LEFT -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    10,
                    (tags[i] as View).height / 2,
                    baseXy,
                    listener
                )
                CircleType.RIGHT -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    (tags[i] as View).width - 10,
                    (tags[i] as View).height / 2,
                    baseXy,
                    listener
                )
                CircleType.TOP_LEFT -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    10,
                    10,
                    baseXy,
                    listener
                )
                CircleType.TOP_RIGHT -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    (tags[i] as View).width - 10,
                    10,
                    baseXy,
                    listener
                )
                CircleType.BOTTOM_LEFT -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    10,
                    (tags[i] as View).height - 10,
                    baseXy,
                    listener
                )
                CircleType.BOTTOM_RIGHT -> startCircleAnimation(
                    zoomType,
                    duration,
                    delayStartTime,
                    (tags[i] as View).width - 10,
                    (tags[i] as View).height - 10,
                    baseXy,
                    listener
                )
            }
        }
    }

    /**
     * 路径动画
     *
     * @param path     路径
     * @param duration 动画时间
     */
    fun startPathAnimation(path: Path?, duration: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                for (i in tags.indices) {
                    val anim = ObjectAnimator.ofFloat(tags[i] as View, View.X, View.Y, path)
                    anim.duration = duration.toLong()
                    anim.start()
                }
            }
        } catch (e: Exception) {
        }
    }
}