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
import com.louisgeek.dialogview.wrapper.DialogViewConfig;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class DialogView extends DialogFragment {
    private static final String TAG = "ClassicDialogFragment";
    private final static String TITLE_KEY = "TITLE_KEY";
    private final static String MESSAGE_KEY = "MESSAGE_KEY";
    private DialogViewConfig mDialogViewConfig = new DialogViewConfig();
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
        this.setCancelable(mDialogViewConfig.isCancelable());

        if (mDialogViewConfig.getCustomTitleView() != null) {
            builder.setCustomTitle(mDialogViewConfig.getCustomTitleView());
        }
        if (mDialogViewConfig.getContentView() != null) {
            builder.setView(mDialogViewConfig.getContentView());
        }
        if (!TextUtils.isEmpty(mDialogViewConfig.getOkText())) {
            builder.setPositiveButton(mDialogViewConfig.getOkText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (mDialogViewConfig.getOnBtnClickListener() != null) {
                        mDialogViewConfig.getOnBtnClickListener().onBtnClickOk(dialogInterface);
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(mDialogViewConfig.getCancelText())) {
            builder.setNegativeButton(mDialogViewConfig.getCancelText(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (mDialogViewConfig.getOnBtnClickListener() != null) {
                        mDialogViewConfig.getOnBtnClickListener().onBtnClickCancel(dialogInterface);
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
        Log.e(TAG, "DialogView isDismissed: " + mDismissed + this);
//        Log.e(TAG, "DialogView mShownByMe: " + mShownByMe + this);
        return mDismissed;
    }

    private void setDialogViewConfig(DialogViewConfig dialogViewConfig) {
        mDialogViewConfig = dialogViewConfig;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        //### super.show(manager, tag);
        DialogFragmentShowHelper.show(manager, this, tag);
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        //### return super.show(transaction, tag);
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
            if (mDialogViewConfig.getLayoutWidth() != null) {
                width = mDialogViewConfig.getLayoutWidth();
            }
            if (mDialogViewConfig.getLayoutHeight() != null) {
                height = mDialogViewConfig.getLayoutHeight();
            }
            //加这个，否则无法实现百分百全屏
            if (mDialogViewConfig.getBackgroundColorValue() != null) {
                int backgroundColor = Integer.parseInt(mDialogViewConfig.getBackgroundColorValue());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));
            } else {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void updateDimAmount(float dimAmount) {
        Dialog dialog = this.getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setDimAmount(dimAmount); //0f 全透明 1f 全黑
        }
    }

    private void updateLayoutSize(int width, int height) {
        Dialog dialog = this.getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(width, height); //任意一个为 0，可以实现事件穿透
        }
    }

    public View getContentView() {
        return mDialogViewConfig.getContentView();
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private DialogViewConfig dialogViewConfig = new DialogViewConfig(); //非关键参数

        public Builder(Context context) {
            //必要参数通过构造函数传入
            this.context = context;
        }

        public Builder setContentView(int layoutResId) {
            View contentView = LayoutInflater.from(context).inflate(layoutResId, null);
            this.dialogViewConfig.setContentView(contentView);
            return this;
        }

        public Builder setContentView(View contentView) {
            this.dialogViewConfig.setContentView(contentView);
            return this;
        }

        public Builder setCustomTitleView(int layoutResId) {
            View contentView = LayoutInflater.from(context).inflate(layoutResId, null);
            this.dialogViewConfig.setCustomTitleView(contentView);
            return this;
        }

        public Builder setCustomTitleView(View contentView) {
            this.dialogViewConfig.setCustomTitleView(contentView);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int resid) {
            this.title = context.getString(resid);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int resid) {
            this.message = context.getString(resid);
            return this;
        }

        public Builder setOkText(String okText) {
            dialogViewConfig.setOkText(okText);
            return this;
        }

        public Builder setOkText(int resid) {
            dialogViewConfig.setOkText(context.getString(resid));
            return this;
        }

        public Builder setCancelText(String cancelText) {
            dialogViewConfig.setCancelText(cancelText);
            return this;
        }

        public Builder setCancelText(int resid) {
            dialogViewConfig.setCancelText(context.getString(resid));
            return this;
        }

        public Builder setLayoutWidth(int layoutWidth) {
            dialogViewConfig.setLayoutWidth(layoutWidth);
            return this;
        }

        public Builder setLayoutHeight(int layoutHeight) {
            dialogViewConfig.setLayoutHeight(layoutHeight);
            return this;
        }

        public Builder setBackgroundColorValue(String backgroundColorValue) {
            dialogViewConfig.setBackgroundColorValue(backgroundColorValue);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            dialogViewConfig.setCancelable(cancelable);
            return this;
        }

        public Builder setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
            this.dialogViewConfig.setOnBtnClickListener(onBtnClickListener);
            return this;
        }

        public DialogView build() {
            DialogView dialogView = DialogView.newInstance(title, message);
            dialogView.setDialogViewConfig(dialogViewConfig);
            return dialogView;
        }
    }
}
