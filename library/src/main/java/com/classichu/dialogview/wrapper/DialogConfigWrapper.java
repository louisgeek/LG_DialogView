package com.classichu.dialogview.wrapper;

import android.view.View;

import com.classichu.dialogview.ui.ClassicDialogFragment;

import java.io.Serializable;

/**
 * Created by louisgeek on 2017/3/2.
 */

public class DialogConfigWrapper implements Serializable {

    private View contentView;
    private View customTitleView;
    private String okText;
    private String cancelText;
    private boolean cancelable;

    public float getWidthPercentValue() {
        return widthPercentValue;
    }
    public float getHeightPercentValue() {
        return heightPercentValue;
    }
    public void setWidthPercentValue(float widthPercentValue) {
        this.widthPercentValue = widthPercentValue;
    }
    public void setHeightPercentValue(float heightPercentValue) {
        this.heightPercentValue = heightPercentValue;
    }
    private float widthPercentValue;//接受0~100 不包括0
    private float heightPercentValue;//接受0~100 不包括0

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public String getOkText() {
        return okText;
    }

    public void setOkText(String okText) {
        this.okText = okText;
    }

    public String getCancelText() {
        return cancelText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    private ClassicDialogFragment.OnBtnClickListener onBtnClickListener;

    public ClassicDialogFragment.OnBtnClickListener getOnBtnClickListener() {
        return onBtnClickListener;
    }

    public void setOnBtnClickListener(ClassicDialogFragment.OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    public View getContentView() {
        return contentView;
    }

    public void setCustomTitleView(View customTitleView) {
        this.customTitleView = customTitleView;
    }

    public View getCustomTitleView() {
        return customTitleView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

}
