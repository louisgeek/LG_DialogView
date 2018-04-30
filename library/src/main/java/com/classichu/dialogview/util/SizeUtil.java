package com.classichu.dialogview.util;

import android.content.res.Resources;

/**
 * Created by louisgeek on 2016/6/17.
 */
public class SizeUtil {
    public static int dp2px(float dp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
