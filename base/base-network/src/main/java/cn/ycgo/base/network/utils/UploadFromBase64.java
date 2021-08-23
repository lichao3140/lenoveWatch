package cn.ycgo.base.network.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import cn.ycgo.base.common.utils.ContextProvider;
import cn.ycgo.base.common.utils.LogUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: luoh
 * @Description: 用于上传的图片转base64字符串
 * @CreateDate: 2019/3/4 15:20
 */
public class UploadFromBase64 {
    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, ContextProvider.INSTANCE.getDisplayWidth(), ContextProvider.INSTANCE.getDisplayHeight());
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public static byte[] pathToByte(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        // 这里的JPEG 如果换成PNG，那么压缩的就有600kB这样
        bm.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] b = baos.toByteArray();
        Log.d("d", "压缩后的大小=" + b.length);
        return b;
    }

    public File bitmapToFile(Bitmap bitmap, String fileName) {
        File file = new File(ContextProvider.context.getExternalCacheDir(), fileName + ".png");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File pathToFile(String filePath) {
        return pathToFile(filePath, System.currentTimeMillis() + "");
    }

    public static File pathToFile(String filePath, String fileName) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        // 这里的JPEG 如果换成PNG，那么压缩的就有600kB这样
        bm.compress(Bitmap.CompressFormat.JPEG, 75, baos);

//        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        File file = new File(ContextProvider.context.getExternalCacheDir(), fileName + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LogUtils.INSTANCE.d("压缩后的大小=--->>>" + file.length());
        recycleBitmap(bm);
        return file;
    }

    //释放
    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }
}
