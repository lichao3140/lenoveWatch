# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
#noinspection ShrinkerUnresolvedReference
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}
##---------------End: proguard configuration for Gson  ----------

-optimizationpasses 5                                                           # 指定代码的压缩级别
-dontusemixedcaseclassnames                                                     # 是否使用大小写混合
-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar

#noinspection ShrinkerUnresolvedReference
-keep public class com.android.vending.licensing.ILicensingService              # 保持哪些类不被混淆

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
  #noinspection ShrinkerUnresolvedReference
  public static final android.os.Parcelable$Creator *;
}

#保持自定义组件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

 -keep class me.jessyan.autosize.** { *; }
 -keep interface me.jessyan.autosize.** { *; }

 ###-------- Gson 相关的混淆配置--------
 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class sun.misc.Unsafe { *; }

 #EventBus
 -keepclassmembers class ** {
     public void onEvent*(**);
 }
 -dontwarn com.google.gson.**
 -dontwarn okio.*
 -dontwarn Android.support.**
 -dontwarn android.support.design.**
 -keep class android.support.design.** { *; }
 -keep interface android.support.design.** { *; }
 -keep public class android.support.design.R$* { *; }
 -keep public class android.support.v7.widget.** { *; }
 -keep public class android.support.v7.internal.widget.** { *; }
 -keep public class android.support.v7.internal.view.menu.** { *; }
 -keep public class * extends android.support.v4.view.ActionProvider {
     public <init>(android.content.Context);
 }
 ## New rules for EventBus 3.0.x ##
 # http://greenrobot.org/eventbus/documentation/proguard/
 -keepattributes *Annotation*
 -keepclassmembers class ** {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }
 # Only required if you use AsyncExecutor
 -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     <init>(java.lang.Throwable);
 }
 # Glide specific rules #
 # https://github.com/bumptech/glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
     **[] $VALUES;
     public *;
 }

-keep class  com.lxc.amap.storage.bean.** {*;}
-keep class  com.atuan.datepickerlibrary.** {*;}
-keep class  com.tianding.modules.easemob.storage.bean.** {*;}
-keep class  org.song.videoplayer.** {*;}
-keep class  com.lxc.thirdparty.storage.bean.** {*;}
-keep class  com.ycgo.components.ad.storage.bean.** {*;}
-keep class com.ycgo.components.browser.bean.** {*;}
-keep class com.ycgo.modules.home.storage.bean.** {*;}
-keep class com.ycgo.components.information.storage.bean.** {*;}
-keep class com.ycgo.components.medicine.storage.bean.** {*;}
-keep class com.ycgo.modules.mine.storage.bean.** {*;}
-keep class com.ycgo.components.piazza.storage.bean.** {*;}
-keep class com.ycgo.components.share.storage.bean.** {*;}
-keep class com.ycgo.components.store.storage.bean.** {*;}
-keep class com.ycgo.components.user.storage.bean.** {*;}
-keep class com.ycgo.base.medial.storage.bean.** {*;}
-keep class  com.ycgo.base.storage.bean.** {*;}
-keep class  com.ycgo.base.network.storage.bean.** {*;}


 #---------------------------------2.第三方库---------------------------------
 #okhttp3
 -dontwarn com.squareup.okhttp3.**
 -keep class com.squareup.okhttp3.** { *;}
 -keep class okhttp3.** { *;}
 -keep class okio.** { *;}
 -dontwarn sun.security.**
 -keep class sun.security.** { *;}
 -dontwarn okio.**
 -dontwarn okhttp3.**

 #retrofit2
 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepattributes Signature
 -keepattributes Exceptions
 -dontwarn org.robovm.**
 -keep class org.robovm.** { *; }

 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }
 -dontnote rx.internal.util.PlatformDependent
 # Retrofit, OkHttp, Gson
 -keep class com.squareup.okhttp.** { *; }
 -keep interface com.squareup.okhttp.** { *; }
 -dontwarn com.squareup.okhttp.**
 -dontwarn rx.**
 -dontwarn retrofit.**
 -keep class retrofit.** { *; }
 -keepclasseswithmembers class * {
     @retrofit.http.* <methods>;
 }
 -keep class sun.misc.Unsafe { *; }
 -dontwarn java.nio.file.*
 -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

 #解决使用Retrofit+rxJava联网时，在6.0系统出现java.lang.InternalError奔溃的问题:http://blog.csdn.net/mp624183768/article/details/79242147
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }

 -keep class rx.schedulers.ImmediateScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.TestScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.Schedulers {
     public static ** test();
 }

 #JSONobject
 -keep class org.json.** {*; }
 #eventbus
 -keepattributes *Annotation*
 -keepclassmembers class ** {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }

 # Only required if you use AsyncExecutor
 -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     <init>(java.lang.Throwable);
 }



 -keep class com.sensorsdata.analytics.android.** { *; }
 -keep public class com.alibaba.android.arouter.routes.**{*;}
 -keep public class com.alibaba.android.arouter.facade.**{*;}
 -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

 # 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
 -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

 -dontwarn java.util.concurrent.Flow*

 -keep class com.huantansheng.easyphotos.models.** { *; }

#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
#搜索
-keep   class com.amap.api.services.**{*;}

# 环信 push
  -dontwarn com.hyphenate.push.***
  -keep class com.hyphenate.push.** {*;}

  -keep class com.hyphenate.** {*;}
  -dontwarn  com.hyphenate.**

  -keep class com.tencent.mm.opensdk.** {
      *;
  }

  -keep class com.tencent.wxop.** {
      *;
  }

  -keep class com.tencent.mm.sdk.** {
      *;
  }