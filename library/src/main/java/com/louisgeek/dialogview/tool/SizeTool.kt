package com.louisgeek.dialogview.tool

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by louisgeek on 2021/11/4.
 */
internal object SizeTool {
    @JvmStatic
    fun dp2px(px: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            px.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}