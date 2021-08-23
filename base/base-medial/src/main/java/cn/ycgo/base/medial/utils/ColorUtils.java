package cn.ycgo.base.medial.utils;

import android.app.Notification;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.app.NotificationCompat;

import cn.ycgo.base.common.utils.ContextProvider;

import java.util.LinkedList;

/**
 * Created by admin
 * on 2018/5/14.
 * 颜色相关工具类
 */
public class ColorUtils {
    /**
     * 对比两个颜色是否相似
     *
     * @param baseColor
     * @param color
     * @return
     */
    public static boolean isColorSimilar(int baseColor, int color) {
        int simplebacecolor = baseColor | 0xff000000;
        int simplecolor = color | 0xff000000;
        int baseRed = Color.red(simplebacecolor) - Color.red(simplecolor);
        int baseGreen = Color.green(simplebacecolor) - Color.green(simplecolor);
        int basseBlue = Color.blue(simplebacecolor) - Color.blue(simplecolor);
        double value = Math.sqrt(baseRed * baseRed + baseGreen * baseGreen + basseBlue * basseBlue);
        if (value < 220) {
            return true;
        } else {
            return false;
        }
    }

    public static String getColorHexa(double alpha, String color) {
        String hexa = color;
        if (hexa.length() > 6) {
            hexa = hexa.substring(hexa.length() - 6, hexa.length());
        }
        int al = (int) (255 * alpha);
        String alstr = Integer.toHexString(Math.abs(al));
        if (alstr.length() < 2) {
            alstr = "0" + Integer.toHexString(Math.abs(al));
        }
        String costr = "#" + alstr + hexa;
        return costr;
    }

    /**
     * int color颜色值转Hexa字符串颜色值
     *
     * @param color
     * @return
     */
    public static String getColorHexa(int color) {
        String hexa = Integer.toHexString(color) + "";
        if (hexa.length() > 6) {
            hexa = hexa.substring(hexa.length() - 6, hexa.length());
        } else if (hexa.length() < 6 && hexa.length() >= 3) {
            char[] chr = hexa.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (int i = chr.length - 3; i < chr.length; i++) {
                sb.append(chr[i] + "" + chr[i]);
            }
            hexa = sb.toString();
        }
        return "#" + hexa;
    }

    public static String getColorHexa(float alpha, int color) {
        String hexa = Integer.toHexString(color) + "";
        if (hexa.length() > 6) {
            hexa = hexa.substring(hexa.length() - 6, hexa.length());
        } else if (hexa.length() < 6 && hexa.length() >= 3) {
            char[] chr = hexa.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (int i = chr.length - 3; i < chr.length; i++) {
                sb.append(chr[i] + "" + chr[i]);
            }
            hexa = sb.toString();
        }
        if (hexa.length() > 6) {
            hexa = hexa.substring(hexa.length() - 6, hexa.length());
        }
        int al = (int) (255 * alpha);
        String alstr = Integer.toHexString(Math.abs(al));
        if (alstr.length() < 2) {
            alstr = "0" + Integer.toHexString(Math.abs(al));
        }
        String costr = "#" + alstr + hexa;
        return costr;
    }

    /**
     * colorres颜色值转Hexa字符串颜色值
     *
     * @param color Res颜色值
     * @return
     */
    public static String getColorHexaRes(@ColorRes int color) {
        return getColorHexa(ContextProvider.INSTANCE.getColor(color));
    }


    /**
     * 获取通知栏颜色
     *
     * @param context
     * @return
     */
    public static int getNotificationColor(Context context) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Notification notification = builder.build();
            int layoutId = notification.contentView.getLayoutId();
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
            if (viewGroup.findViewById(android.R.id.title) != null) {
                return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
            }
            return findColor(viewGroup);
        } catch (Exception e) {
            return Color.WHITE;
        }
    }

    private static int findColor(ViewGroup viewGroupSource) {
        try {
            int color = Color.TRANSPARENT;
            LinkedList<ViewGroup> viewGroups = new LinkedList<>();
            viewGroups.add(viewGroupSource);
            while (viewGroups.size() > 0) {
                ViewGroup viewGroup1 = viewGroups.getFirst();
                for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                    if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                        viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                    } else if (viewGroup1.getChildAt(i) instanceof TextView) {
                        if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor() != -1) {
                            color = ((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                        }
                    }
                }
                viewGroups.remove(viewGroup1);
            }
            return color;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 取色
     *
     * @param context
     * @param androidAttribute
     * @return
     */
    public static int fetchContextColor(Context context, int androidAttribute) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{androidAttribute});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
