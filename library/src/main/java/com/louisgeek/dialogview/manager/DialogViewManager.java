package com.louisgeek.dialogview.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.louisgeek.dialogview.listener.OnAutoHideListener;
import com.louisgeek.dialogview.listener.OnBtnClickListener;
import com.louisgeek.dialogview.listener.OnEditBtnClickListener;
import com.louisgeek.dialogview.DialogView;
import com.louisgeek.dialogview.R;
import com.louisgeek.dialogview.tool.SizeTool;

import java.lang.ref.WeakReference;


/**
 * Created by louisgeek on 2016/12/15.
 */

public class DialogViewManager {
    /**
     * ================================   LoadingDialog  ================================
     */
    private static WeakReference<DialogView> mLoadingDialogFragmentWeakReference;

    public static void hideLoadingDialogAutoAfterTip(String titleTip) {
        hideLoadingDialogAutoAfterTip(titleTip, -1, null);
    }

    public static void hideLoadingDialogAutoAfterTip(String titleTip, long durationMillis) {
        hideLoadingDialogAutoAfterTip(titleTip, durationMillis, null);
    }

    public static void hideLoadingDialogAutoAfterTip(String titleTip, long durationMillis, final OnAutoHideListener autoHideListener) {
        if (mLoadingDialogFragmentWeakReference != null && mLoadingDialogFragmentWeakReference.get() != null) {
            final DialogView dialogView = mLoadingDialogFragmentWeakReference.get();
            View contentView = dialogView.getContentView();
            Context context = contentView.getContext();
            LinearLayout linearLayout = null;
            if (contentView instanceof LinearLayout) {
                linearLayout = (LinearLayout) contentView;
            }
            if (linearLayout != null) {
                linearLayout.removeAllViews();
                //
                TextView textView = new TextView(context);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText(TextUtils.isEmpty(titleTip) ? "加载中..." : titleTip);
                textView.setPadding(SizeTool.dp2px(15), SizeTool.dp2px(15), SizeTool.dp2px(15), SizeTool.dp2px(15));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                textView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                /**
                 * 显示动画
                 */
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                //默认1000
                alphaAnimation.setDuration(durationMillis < 0 ? 1000 : durationMillis);
                textView.setAnimation(alphaAnimation);
                //
                linearLayout.addView(textView);
            }
            if (linearLayout == null || titleTip == null) {
                dialogView.dismissAllowingStateLoss();
                if (autoHideListener != null) {
                    autoHideListener.onAutoHide();
                }
            } else {
                //延迟关闭
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogView.dismissAllowingStateLoss();
                        if (autoHideListener != null) {
                            autoHideListener.onAutoHide();
                        }
                    }
                }, 2 * 1000);

            }
        }
    }

    public static void hideLoadingDialog() {
        if (mLoadingDialogFragmentWeakReference != null && mLoadingDialogFragmentWeakReference.get() != null) {
            DialogView dialogView = mLoadingDialogFragmentWeakReference.get();
            dialogView.dismissAllowingStateLoss();
        }
    }

    public static void showLoadingDialog(FragmentActivity fragmentActivity) {
        showLoadingDialog(fragmentActivity, false);
    }

    public static void showLoadingDialog(FragmentActivity fragmentActivity, boolean showClose) {
        showLoadingDialog(fragmentActivity, null, showClose);
    }

    public static void showLoadingDialog(FragmentActivity fragmentActivity, String title, boolean showClose) {
        if (fragmentActivity == null || fragmentActivity.isFinishing()) {
            return;
        }
        if (mLoadingDialogFragmentWeakReference != null
                && mLoadingDialogFragmentWeakReference.get() != null
                && !mLoadingDialogFragmentWeakReference.get().isDismissed()) {
            return;
        }
        //
        hideLoadingDialog();
        //
        LinearLayout linearLayout = new LinearLayout(fragmentActivity);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setBackgroundColor(Color.WHITE);

        ProgressBar progressBar = new ProgressBar(fragmentActivity);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(SizeTool.dp2px(56), SizeTool.dp2px(56)));
        progressBar.setPadding(SizeTool.dp2px(10), SizeTool.dp2px(10), SizeTool.dp2px(10), SizeTool.dp2px(10));
        linearLayout.addView(progressBar);

        TextView textView = new TextView(fragmentActivity);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(TextUtils.isEmpty(title) ? "加载中..." : title);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        linearLayout.addView(textView);

        if (showClose) {
            ImageView imageView = new ImageView(fragmentActivity);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(SizeTool.dp2px(40), SizeTool.dp2px(40)));
            imageView.setImageDrawable(ContextCompat.getDrawable(fragmentActivity,
                    R.drawable.ic_close_black_24dp));
            imageView.setPadding(SizeTool.dp2px(5), SizeTool.dp2px(5), SizeTool.dp2px(5), SizeTool.dp2px(5));
            //  imageView.getLayoutParams().
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideLoadingDialog();
                }
            });
            imageView.setColorFilter(ContextCompat.getColor(fragmentActivity, R.color.colorAccent));
            linearLayout.addView(imageView);
        }
        DialogView loadingDialogView = new DialogView.Builder(fragmentActivity)
                .setContentView(linearLayout)
                .setCancelable(false)
                .setWidthPercentValue(45F)
                .build();

        loadingDialogView.show(fragmentActivity.getSupportFragmentManager(), "showLoadingDialog");
        mLoadingDialogFragmentWeakReference = new WeakReference<>(loadingDialogView);
    }

    /**
     * ================================   ClassicDialog  ================================
     */
    private static WeakReference<DialogView> mClassicDialogFragmentWeakReference;

    public static void showClassicDialog(FragmentActivity fragmentActivity, String title, String message,
                                         OnBtnClickListener onBtnClickListener) {
        showClassicDialog(fragmentActivity, title, message, onBtnClickListener, "确定", "取消", "showClassicDialog");
    }

    public static void showClassicDialog(FragmentActivity fragmentActivity, String title, String message,
                                         OnBtnClickListener onBtnClickListener,
                                         String okText, String cancelText, String tag) {
        if (fragmentActivity == null || fragmentActivity.isFinishing() || message == null) {
            return;
        }
        //
        hideClassicDialog();
        //
        DialogView dialogView = new DialogView.Builder(fragmentActivity)
                .setTitle(title)
                .setMessage(message)
                .setOnBtnClickListener(onBtnClickListener)
                .setOkText(okText)
                .setCancelText(cancelText)
//                .setBackgroundColorValue(backgroundColorValue)
                .build();
        dialogView.show(fragmentActivity.getSupportFragmentManager(), tag);
        mClassicDialogFragmentWeakReference = new WeakReference<>(dialogView);
    }

    public static void hideClassicDialog() {
        if (mClassicDialogFragmentWeakReference != null && mClassicDialogFragmentWeakReference.get() != null) {
            DialogView dialogView = mClassicDialogFragmentWeakReference.get();
            dialogView.dismissAllowingStateLoss();
        }
    }

    /**
     * ================================   TipDialog  ================================
     */
    private static WeakReference<DialogView> mTipDialogFragmentWeakReference;


    public static void showTipDialog(FragmentActivity fragmentActivity, String title, String message,
                                     OnBtnClickListener onBtnClickListener) {
        showTipDialog(fragmentActivity, title, null, message, onBtnClickListener, "确定", "showTipDialog");
    }

    public static void showTipDialog(FragmentActivity fragmentActivity, String title, String message) {
        showTipDialog(fragmentActivity, title, null, message, null, "确定", "showTipDialog");
    }

    public static void showTipDialog(FragmentActivity fragmentActivity, View customTitleView, String message) {
        showTipDialog(fragmentActivity, null, customTitleView, message, null, "确定", "showTipDialog");
    }

    public static void showTipDialog(FragmentActivity fragmentActivity, String title, View customTitleView, String message,
                                     OnBtnClickListener onBtnClickListener, String okText, String tag) {
        if (fragmentActivity == null || fragmentActivity.isFinishing() || message == null) {
            return;
        }
        hideTipDialog();
        //
        DialogView.Builder builder = new DialogView.Builder(fragmentActivity);
        if (title != null) {
            builder.setTitle(title);
        }
        if (customTitleView != null) {
            builder.setCustomTitleView(customTitleView);
        }
        builder.setMessage(message);
        builder.setOkText(okText);
        builder.setOnBtnClickListener(onBtnClickListener);

        DialogView dialogView = builder.build();
        dialogView.show(fragmentActivity.getSupportFragmentManager(), tag);
        mTipDialogFragmentWeakReference = new WeakReference<>(dialogView);
    }

    public static void hideTipDialog() {
        if (mTipDialogFragmentWeakReference != null && mTipDialogFragmentWeakReference.get() != null) {
            DialogView dialogView = mTipDialogFragmentWeakReference.get();
            dialogView.dismissAllowingStateLoss();
        }
    }

    /**
     * ================================   EditDialog  ================================
     */
    private static WeakReference<DialogView> mEditDialogFragmentWeakReference;

    /**
     * @param fragmentActivity
     * @param title
     * @param message
     * @param listener
     */
    public static void showEditDialog(FragmentActivity fragmentActivity, String title, String message,
                                      OnEditBtnClickListener listener) {
        showEditDialog(fragmentActivity, title, message, listener, "确定", "取消", "showEditDialog", "请输入信息");
    }

    /**
     * @param fragmentActivity
     * @param title
     * @param message
     * @param onEditBtnClickListener
     * @param okText
     * @param cancelText
     * @param tag
     */
    public static void showEditDialog(FragmentActivity fragmentActivity, String title, String message,
                                      final OnEditBtnClickListener onEditBtnClickListener, String okText, String cancelText, String tag, String hintText) {
        if (fragmentActivity == null || fragmentActivity.isFinishing() || message == null) {
            return;
        }
        hideEditDialog();
        //
        LinearLayout linearLayout = new LinearLayout(fragmentActivity);
        linearLayout.setGravity(LinearLayout.HORIZONTAL);
        //setMargins 的 LinearLayout.LayoutParams 需要设置在editText上 才有效
        LinearLayout.LayoutParams ll_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll_lp.setMargins(SizeTool.dp2px(16), 0, SizeTool.dp2px(16), 0);
        final AppCompatEditText editText = new AppCompatEditText(fragmentActivity);
        editText.setLayoutParams(ll_lp);
        editText.setHint(hintText);
        editText.setHintTextColor(Color.GRAY);
        editText.setLines(1);
        //
        linearLayout.addView(editText);

        DialogView dialogView = new DialogView.Builder(fragmentActivity)
                .setTitle(title)
                .setMessage(message)
                .setContentView(linearLayout)
                .setOnBtnClickListener(new OnBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        if (onEditBtnClickListener != null) {
                            onEditBtnClickListener.onBtnClickOk(dialogInterface, editText.getText().toString());
                        }
                    }

                    @Override
                    public void onBtnClickCancel(DialogInterface dialogInterface) {
                        if (onEditBtnClickListener != null) {
                            onEditBtnClickListener.onBtnClickCancel(dialogInterface);
                        }
                    }
                })
                .setOkText(okText)
                .setCancelText(cancelText)
                .build();
        dialogView.show(fragmentActivity.getSupportFragmentManager(), tag);
        mEditDialogFragmentWeakReference = new WeakReference<>(dialogView);
    }

    private static void hideEditDialog() {
        if (mEditDialogFragmentWeakReference != null && mEditDialogFragmentWeakReference.get() != null) {
            DialogView dialogView = mEditDialogFragmentWeakReference.get();
            dialogView.dismissAllowingStateLoss();
        }
    }
}
