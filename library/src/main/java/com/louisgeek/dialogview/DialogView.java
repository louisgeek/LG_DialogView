package com.louisgeek.dialogview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.louisgeek.dialogview.helper.DialogFragmentShowHelper;
import com.louisgeek.dialogview.listener.OnBtnClickListener;
import com.louisgeek.dialogview.util.ReflectUtil;
import com.louisgeek.dialogview.wrapper.DialogConfigWrapper;
import com.louisgeek.dialogview.tool.ScreenTool;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class DialogView extends DialogFragment {
    private static final String TAG = "ClassicDialogFragment";
    private final static String TITLE_KEY = "TITLE_KEY";
    private final static String MESSAGE_KEY = "MESSAGE_KEY";
    private DialogConfigWrapper mWrapperDialogConfig = new DialogConfigWrapper();
    protected AppCompatActivity mActivity;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        String title = null;
        String message = null;
        if (getArguments() != null) {
            title = getArguments().getString(TITLE_KEY);
            message = getArguments().getString(MESSAGE_KEY);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        //这样设置无效 需要设置DialogFragment的setCancelable方法才有效
        //!!! builder.setCancelable(mWrapperDialogConfig.isCancelable());
        this.setCancelable(mWrapperDialogConfig.isCancelable());

        if (mWrapperDialogConfig.getCustomTitleView() != null) {
            builder.setCustomTitle(mWrapperDialogConfig.getCustomTitleView());
        }
        if (mWrapperDialogConfig.getContentView() != null) {
            builder.setView(mWrapperDialogConfig.getContentView());
        }
        if (!TextUtils.isEmpty(mWrapperDialogConfig.getOkText())) {
            builder.setPositiveButton(mWrapperDialogConfig.getOkText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (mWrapperDialogConfig.getOnBtnClickListener() != null) {
                        mWrapperDialogConfig.getOnBtnClickListener().onBtnClickOk(dialogInterface);
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(mWrapperDialogConfig.getCancelText())) {
            builder.setNegativeButton(mWrapperDialogConfig.getCancelText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (mWrapperDialogConfig.getOnBtnClickListener() != null) {
                        mWrapperDialogConfig.getOnBtnClickListener().onBtnClickCancel(dialogInterface);
                    }
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        if (!TextUtils.isEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_OPTIONS_PANEL);
        } else {
            //隐藏标题
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    return onDialogKeyBackPressed();
                }
                return false;
            }
        });
//         return super.onCreateDialog(savedInstanceState);
        return alertDialog;

    }

    protected boolean onDialogKeyBackPressed() {
        return false;
    }

    private static DialogView newInstance(String title, String message) {
        DialogView dialogFragment = new DialogView();
        Bundle args = new Bundle();
        //标题
        args.putString(TITLE_KEY, title);
        args.putString(MESSAGE_KEY, message);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    public boolean isDismissed() {
        boolean mDismissed = ReflectUtil.getFieldValueFromSuperClass(DialogFragment.class, this, "mDismissed");
//        boolean mShownByMe = ReflectTool.getFieldValueFromSuperClass(DialogFragment.class, this, "mShownByMe");
        Log.e(TAG, "mProgressDialogFragment2 isDismissed: " + mDismissed + this);
//        Log.e(TAG, "mProgressDialogFragment2 mShownByMe: " + mShownByMe + this);
        return mDismissed;
    }

    private void setWrapperDialogConfig(DialogConfigWrapper wrapperDialogConfig) {
        mWrapperDialogConfig = wrapperDialogConfig;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        //#### super.show(manager, tag);
        DialogFragmentShowHelper.show(manager, this, tag);
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        //#### return super.show(transaction, tag);
        return DialogFragmentShowHelper.show(transaction, this, tag);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = this.getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = dialog.getWindow().getAttributes().width;
            int height = dialog.getWindow().getAttributes().height;
            //不设置这个 部分机子上非透明背景会出现四周黑边框（不过启用硬件加速可能可以去掉边框）
            dialog.getWindow().setFormat(PixelFormat.RGBA_8888);
            if (mWrapperDialogConfig.getWidthPercentValue() > 0F) {
                width = (int) (ScreenTool.getScreenWidth() * mWrapperDialogConfig.getWidthPercentValue() * 1.0F / 100);
            }
            if (mWrapperDialogConfig.getHeightPercentValue() > 0F) {
                height = (int) (ScreenTool.getScreenHeight() * mWrapperDialogConfig.getHeightPercentValue() * 1.0F / 100);
            }
            //加这个，否则无法实现百分百全屏
            if (mWrapperDialogConfig.getBackgroundColorValue() != null) {
                int backgroundColor = Integer.parseInt(mWrapperDialogConfig.getBackgroundColorValue());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));
            } else {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.getWindow().setLayout(width, height);
        }
    }


    public View getContentView() {
        return mWrapperDialogConfig.getContentView();
    }

    public static class Builder {
        private String title;
        private String message;
        private Context context;
        private DialogConfigWrapper wrapperDialogConfig = new DialogConfigWrapper();


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(int layoutResId) {
            setContentView(LayoutInflater.from(context).inflate(layoutResId, null));
            return this;
        }

        public Builder setContentView(View contentView) {
            this.wrapperDialogConfig.setContentView(contentView);
            return this;
        }

        public Builder setCustomTitleView(int layoutResId) {
            setCustomTitleView(LayoutInflater.from(context).inflate(layoutResId, null));
            return this;
        }

        public Builder setCustomTitleView(View contentView) {
            this.wrapperDialogConfig.setCustomTitleView(contentView);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int resid) {
            setTitle(context.getString(resid));
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int resid) {
            setMessage(context.getString(resid));
            return this;
        }

        public Builder setOkText(String okText) {
            wrapperDialogConfig.setOkText(okText);
            return this;
        }

        public Builder setOkText(int resid) {
            setOkText(context.getString(resid));
            return this;
        }

        public Builder setCancelText(String cancelText) {
            wrapperDialogConfig.setCancelText(cancelText);
            return this;
        }

        public Builder setCancelText(int resid) {
            setCancelText(context.getString(resid));
            return this;
        }

        public Builder setWidthPercentValue(float widthPercentValue) {
            wrapperDialogConfig.setWidthPercentValue(widthPercentValue);
            return this;
        }

        public Builder setHeightPercentValue(float heightPercentValue) {
            wrapperDialogConfig.setHeightPercentValue(heightPercentValue);
            return this;
        }

        public Builder setBackgroundColorValue(String backgroundColorValue) {
            wrapperDialogConfig.setBackgroundColorValue(backgroundColorValue);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            wrapperDialogConfig.setCancelable(cancelable);
            return this;
        }

        public Builder setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
            this.wrapperDialogConfig.setOnBtnClickListener(onBtnClickListener);
            return this;
        }

        public DialogView build() {
            DialogView dialogView = DialogView.newInstance(title, message);
            dialogView.setWrapperDialogConfig(wrapperDialogConfig);
            return dialogView;
        }
    }
}
