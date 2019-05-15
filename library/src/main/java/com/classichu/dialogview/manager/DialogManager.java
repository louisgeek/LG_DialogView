package com.classichu.dialogview.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classichu.dialogview.R;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.classichu.dialogview.util.SizeUtil;


/**
 * Created by louisgeek on 2016/12/15.
 */

public class DialogManager {

    private static ClassicDialogFragment mLoadingClassicDialogFragment;

    public static void hideLoadingDialogAutoAfterTip(String titleTip) {
        hideLoadingDialogAutoAfterTip(titleTip, -1, null);
    }

    public static void hideLoadingDialogAutoAfterTip(String titleTip, long durationMillis) {
        hideLoadingDialogAutoAfterTip(titleTip, durationMillis, null);
    }

    public static void hideLoadingDialogAutoAfterTip(String titleTip, long durationMillis, final OnAutoHide autoHide) {
        if (mLoadingClassicDialogFragment != null) {
            View contentView = mLoadingClassicDialogFragment.getDialogContentView();
            Context context = contentView.getContext();
            LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.id_ui_in_code_loading_one);
            if (linearLayout != null) {

                TextView textView = new TextView(contentView.getContext());
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText(TextUtils.isEmpty(titleTip) ? "加载中..." : titleTip);
                textView.setPadding(SizeUtil.dp2px(15), SizeUtil.dp2px(15), SizeUtil.dp2px(15), SizeUtil.dp2px(15));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

                if ("darker_gray".equals(linearLayout.getTag(R.id.id_hold_loading_info))) {
                    textView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                }
                /**
                 * 显示动画
                 */
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation.setDuration(durationMillis < 0 ? 1000 : durationMillis);//默认1000
                textView.setAnimation(alphaAnimation);

                linearLayout.removeAllViews();
                linearLayout.addView(textView);
            }
            if (linearLayout == null || titleTip == null) {
                mLoadingClassicDialogFragment.dismissAllowingStateLoss();
                mLoadingClassicDialogFragment = null;
                if (autoHide != null) {
                    autoHide.autoHide();
                }
            } else {
                //延迟关闭
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingClassicDialogFragment.dismissAllowingStateLoss();
                        mLoadingClassicDialogFragment = null;
                        if (autoHide != null) {
                            autoHide.autoHide();
                        }
                    }
                }, 2 * 1000);

            }
        }
    }

    public static void hideLoadingDialog() {
        if (mLoadingClassicDialogFragment != null) {
            //### mLoadingClassicDialogFragment.dismiss();
            mLoadingClassicDialogFragment.dismissAllowingStateLoss();
            mLoadingClassicDialogFragment = null;
        }
    }

    public static boolean isShowLoading() {
        if (mLoadingClassicDialogFragment != null
                && mLoadingClassicDialogFragment.getDialog() != null
                && mLoadingClassicDialogFragment.getDialog().isShowing()) {
            return true;
        }
        return false;
    }

    public static void showLoadingDialog(FragmentActivity fragmentActivity) {
        showLoadingDialog(fragmentActivity, false);
    }

    public static void showLoadingDialog(FragmentActivity fragmentActivity, boolean showClose) {
        showLoadingDialog(fragmentActivity, null, showClose);
    }

    public static void showLoadingDialog(FragmentActivity fragmentActivity, String title, boolean showClose) {
        if (fragmentActivity == null || fragmentActivity.isFinishing()) {
            //KLog.d("showLoadingDialog return");
            return;
        }
        if (mLoadingClassicDialogFragment != null
                && mLoadingClassicDialogFragment.getDialog() != null
                && mLoadingClassicDialogFragment.getDialog().isShowing()) {
            return;
        }
        //
        hideLoadingDialog();

        LinearLayout linearLayout = new LinearLayout(fragmentActivity);
        linearLayout.setId(R.id.id_ui_in_code_loading_one);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setBackgroundColor(Color.WHITE);

        ProgressBar progressBar = new ProgressBar(fragmentActivity);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(SizeUtil.dp2px(56), SizeUtil.dp2px(56)));
        progressBar.setPadding(SizeUtil.dp2px(10), SizeUtil.dp2px(10), SizeUtil.dp2px(10), SizeUtil.dp2px(10));
        linearLayout.addView(progressBar);

        TextView textView = new TextView(fragmentActivity);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(TextUtils.isEmpty(title) ? "加载中..." : title);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        linearLayout.addView(textView);

        if (showClose) {
            ImageView imageView = new ImageView(fragmentActivity);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(SizeUtil.dp2px(40), SizeUtil.dp2px(40)));
            imageView.setImageDrawable(ContextCompat.getDrawable(fragmentActivity, R.drawable.ic_close_black_24dp));
            imageView.setPadding(SizeUtil.dp2px(5), SizeUtil.dp2px(5), SizeUtil.dp2px(5), SizeUtil.dp2px(5));
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
        mLoadingClassicDialogFragment = new ClassicDialogFragment.Builder(fragmentActivity)
                .setContentView(linearLayout)
                .setCancelable(false)
                .setWidthPercentValue(45F)
                .setHeightPercentValue(45F)
                .setBackgroundColorValue(String.valueOf(Color.WHITE))
                .build();

        mLoadingClassicDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "showLoadingDialog");
    }

    /**
     * =================================================================
     */
    public static void showCustomLoadingDialog(FragmentActivity fragmentActivity) {
        if (fragmentActivity == null || fragmentActivity.isFinishing()) {
            //KLog.d("showCustomLoadingDialog return");
            return;
        }
        if (mLoadingClassicDialogFragment != null
                && mLoadingClassicDialogFragment.getDialog() != null
                && mLoadingClassicDialogFragment.getDialog().isShowing()) {
            return;
        }
        //
        hideLoadingDialog();

        View view = LayoutInflater.from(fragmentActivity).inflate(R.layout.layout_classic_dialog_loading, null, false);
        view.setId(R.id.id_ui_in_code_loading_one);
        view.setTag(R.id.id_hold_loading_info, "darker_gray");
        mLoadingClassicDialogFragment = new ClassicDialogFragment.Builder(fragmentActivity)
                .setContentView(view)
                .setCancelable(false)
                .setWidthPercentValue(30F)
                .setHeightPercentValue(30F)
                .setBackgroundColorValue(String.valueOf(Color.TRANSPARENT))
                .build();

        mLoadingClassicDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "showCustomLoadingDialog");
    }

    /**
     * ================================================================
     */

    private static ClassicDialogFragment mClassicDialogFragment;

    /**
     * @param fragmentActivity
     * @param title
     * @param message
     * @param onBtnClickListener
     * @param okText
     * @param cancelText
     * @param tag
     */
    public static void showClassicDialog(FragmentActivity fragmentActivity, String title, String message,
                                         ClassicDialogFragment.OnBtnClickListener onBtnClickListener,
                                         String okText, String cancelText, String tag) {
        if (fragmentActivity == null || fragmentActivity.isFinishing() || message == null) {
            //KLog.d("showAskDialog return");
            return;
        }
        if (mClassicDialogFragment != null) {
            //##mClassicDialogFragment.dismiss();
            mClassicDialogFragment.dismissAllowingStateLoss();
            mClassicDialogFragment = null;
        }
        mClassicDialogFragment = new ClassicDialogFragment.Builder(fragmentActivity)
                .setTitle(title)
                .setMessage(message)
                .setOnBtnClickListener(onBtnClickListener)
                .setOkText(okText)
                .setCancelText(cancelText)
                .setBackgroundColorValue(String.valueOf(Color.WHITE))
                .build();
        mClassicDialogFragment.show(fragmentActivity.getSupportFragmentManager(), tag);
    }

    /**
     * @param fragmentActivity
     * @param title
     * @param message
     * @param onBtnClickListener
     */
    public static void showClassicDialog(FragmentActivity fragmentActivity, String title, String message,
                                         ClassicDialogFragment.OnBtnClickListener onBtnClickListener) {
        showClassicDialog(fragmentActivity, title, message, onBtnClickListener, "确定", "取消", "showClassicDialog");
    }

    private static ClassicDialogFragment mTipDialogFragment;


    public static void showTipDialog(FragmentActivity fragmentActivity, String title, View customTitleView, String message,
                                     ClassicDialogFragment.OnBtnClickListener onBtnClickListener, String okText, String tag) {
        if (fragmentActivity == null || fragmentActivity.isFinishing() || message == null) {
            //KLog.d("showAskDialog return");
            return;
        }
        if (mTipDialogFragment != null) {
            //##mTipDialogFragment.dismiss();
            mTipDialogFragment.dismissAllowingStateLoss();
            mTipDialogFragment = null;
        }
        ClassicDialogFragment.Builder builder = new ClassicDialogFragment.Builder(fragmentActivity);
        if (title != null) {
            builder.setTitle(title);
        }
        if (customTitleView != null) {
            builder.setCustomTitleView(customTitleView);
        }
        builder.setMessage(message);
        builder.setOkText(okText);
        builder.setOnBtnClickListener(onBtnClickListener);
        builder.setBackgroundColorValue(String.valueOf(Color.WHITE));

        mTipDialogFragment = builder.build();
        mTipDialogFragment.show(fragmentActivity.getSupportFragmentManager(), tag);
    }


    public static void showTipDialog(FragmentActivity fragmentActivity, String title, String message,
                                     ClassicDialogFragment.OnBtnClickListener onBtnClickListener) {
        showTipDialog(fragmentActivity, title, null, message, onBtnClickListener, "确定", "showTipDialog");
    }
    public static void showTipDialog(FragmentActivity fragmentActivity, String title, String message) {
        showTipDialog(fragmentActivity, title, null, message, null, "确定", "showTipDialog");
    }
    public static void showTipDialog(FragmentActivity fragmentActivity, View customTitleView, String message) {
        showTipDialog(fragmentActivity, null, customTitleView, message, null, "确定", "showTipDialog");
    }

    private static ClassicDialogFragment mEditDialogFragment;

    /**
     * @param fragmentActivity
     * @param title
     * @param message
     * @param listener
     */
    public static void showEditDialog(FragmentActivity fragmentActivity, String title, String message,
                                      OnEditDialogBtnClickListener listener) {
        showEditDialog(fragmentActivity, title, message, listener, "确定", "取消", "showEditDialog", "请输入信息");
    }

    /**
     * @param fragmentActivity
     * @param title
     * @param message
     * @param listener
     * @param okText
     * @param cancelText
     * @param tag
     */
    public static void showEditDialog(FragmentActivity fragmentActivity, String title, String message,
                                      final OnEditDialogBtnClickListener listener, String okText, String cancelText, String tag, String hintText) {
        if (fragmentActivity == null || fragmentActivity.isFinishing() || message == null) {
            //KLog.d("showAskDialog return");
            return;
        }
        if (mEditDialogFragment != null) {
            //##mEditDialogFragment.dismiss();
            mEditDialogFragment.dismissAllowingStateLoss();
            mEditDialogFragment = null;
        }

        LinearLayout linearLayout = new LinearLayout(fragmentActivity);
        linearLayout.setGravity(LinearLayout.HORIZONTAL);
        //setMargins 的 LinearLayout.LayoutParams 需要设置在editText上 才有效
        LinearLayout.LayoutParams ll_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll_lp.setMargins(SizeUtil.dp2px(15), 0, SizeUtil.dp2px(15), 0);
        final EditText editText = new EditText(fragmentActivity);
        editText.setLayoutParams(ll_lp);
        editText.setHint(hintText);
        editText.setHintTextColor(Color.GRAY);
        editText.setLines(1);
        //
        linearLayout.addView(editText);

        mEditDialogFragment = new ClassicDialogFragment.Builder(fragmentActivity)
                .setTitle(title)
                .setMessage(message)
                .setContentView(linearLayout)
                .setOnBtnClickListener(new ClassicDialogFragment.OnBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        super.onBtnClickOk(dialogInterface);
                        if (listener != null) {
                            listener.onBtnClickOk(dialogInterface, editText.getText().toString());
                        }
                    }

                    @Override
                    public void onBtnClickCancel(DialogInterface dialogInterface) {
                        super.onBtnClickCancel(dialogInterface);
                        if (listener != null) {
                            listener.onBtnClickCancel(dialogInterface);
                        }
                    }
                })
                .setOkText(okText)
                .setCancelText(cancelText)
                .setBackgroundColorValue(String.valueOf(Color.WHITE))
                .build();
        mEditDialogFragment.show(fragmentActivity.getSupportFragmentManager(), tag);
    }

    public abstract static class OnEditDialogBtnClickListener {
        public void onBtnClickOk(DialogInterface dialogInterface, String inputText) {

        }

        public void onBtnClickCancel(DialogInterface dialogInterface) {

        }
    }

    public interface OnAutoHide {
        void autoHide();
    }
}
