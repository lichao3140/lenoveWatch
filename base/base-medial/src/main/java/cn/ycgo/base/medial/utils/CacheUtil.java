package cn.ycgo.base.medial.utils;

import android.os.Environment;

import com.bumptech.glide.Glide;
import cn.ycgo.base.common.utils.ContextProvider;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author luoh
 * @date 2020/9/22.
 * description：
 */
public class CacheUtil {
    /**
     * 获取缓存大小
     *
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize() throws Exception {
        return getFormatSize(getCacheSize());
    }

    /**
     * 获取缓存大小
     *
     * @return
     * @throws Exception
     */
    public static long getGlideCacheSize() throws Exception {
        return getFolderSize(Glide.getPhotoCacheDir(ContextProvider.context));
    }

    /**
     * 获取缓存大小
     *
     * @return
     * @throws Exception
     */
    public static long getCacheSize() throws Exception {
        long cacheSize = getFolderSize(ContextProvider.context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(ContextProvider.context.getExternalCacheDir());
        }
        return cacheSize;
    }

    /**
     * 清除缓存
     */
    public static void clearAllCache() {
        deleteDir(ContextProvider.context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(ContextProvider.context.getExternalCacheDir());
        }
        Glide.get(ContextProvider.context).clearMemory();//清理Glide图片缓存
        new Thread(() -> Glide.get(ContextProvider.context).clearDiskCache()).start();
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件大小
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    //getCacheDir()方法用于获取/data/data//cache目录
    //getFilesDir()方法用于获取/data/data//files目录
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0.0MB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
//        if (gigaByte < 1) {
//            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
//            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
//                    .toPlainString() + "MB";
//        }
        BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
        return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "MB";

//        double teraBytes = gigaByte / 1024;
//        if (teraBytes < 1) {
//            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
//            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
//                    .toPlainString() + "GB";
//        }
//        BigDecimal result4 = new BigDecimal(teraBytes);
//        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
//                + "TB";
    }
}
