package cn.ycgo.base.medial.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by asus on 2017/8/14.
 * 剪贴板工具箱
 * Android提供的剪贴板框架，复制和粘贴不同类型的数据。数据可以是文本，图像，二进制流数据或其它复杂的数据类型。
 */

public class ClipboardUtil {
    public static final String TAG = "ClipboardUtils";

    /**
     * 往Clip中放入数据
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void put(final Context context, final String text) {
        // 往ClipboardManager中可放的数据类型有三种:text URI Intent
        // 类型一:text
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData textCd = ClipData.newPlainText("Text", text);
        clipboard.setPrimaryClip(textCd);
    }

    /**
     * 读取文本
     *
     * @param context 上下文
     */
    public static String get(final Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = null;

        // 无数据时直接返回
        if (!clipboard.hasPrimaryClip()) {
            // showToastShort(context, "剪贴板中无数据");
            return "";
        }

        // 如果是文本信息
        if (clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cdText = clipboard.getPrimaryClip();
            item = cdText.getItemAt(0);
            // 此处是TEXT文本信息
            if (item.getText() == null) {
                // showToastShort(context, "剪贴板中无数据");
                return "";
            } else {
                return item.getText().toString().trim();
            }
        }
        return "";
    }

}
