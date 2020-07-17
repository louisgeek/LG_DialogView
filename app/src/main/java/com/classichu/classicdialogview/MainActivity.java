package com.classichu.classicdialogview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.classichu.dialogview.listener.OnAutoHideListener;
import com.classichu.dialogview.listener.OnBtnClickListener;
import com.classichu.dialogview.listener.OnEditBtnClickListener;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;

public class MainActivity extends AppCompatActivity {
    FragmentActivity fragmentActivity;
    Context context;
    private CountDownTimer countDownTimer = new CountDownTimer(2 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

            DialogManager.hideLoadingDialogAutoAfterTip("加载完成", 1000, new OnAutoHideListener() {
                @Override
                public void onAutoHide() {
                    //do some thing
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        fragmentActivity = this;

        findViewById(R.id.id_btn_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
//                DialogManager.showLoadingDialog(fragmentActivity);
//                DialogManager.showLoadingDialog(fragmentActivity, true);
               /* new AlertDialog.Builder(fragmentActivity)
                        .setTitle("4q34q")
                        .setMessage("dsada")
                        .create().show();*/

                //
                TextView dialogTitleTextView = new TextView(context);
//                SizeUtil.setPaddingLeftTopRight(dialogTitleTextView, 20);
                dialogTitleTextView.setText("温习提示");
                new ClassicDialogFragment.Builder(fragmentActivity)
//                        .setCustomTitleView(dialogTitleTextView)
                        .setTitle("dsadasdas")
                        .setMessage("dsad")
                        .setBackgroundColorValue(String.valueOf(Color.WHITE))
                        .build().show(getSupportFragmentManager(), "dsada");
            }
        });

        findViewById(R.id.id_btn_tip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showTipDialog(fragmentActivity, "温馨提示", "提示内容");
            }
        });

        findViewById(R.id.id_btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showEditDialog(fragmentActivity, "输入框", "输入内容", new OnEditBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface, String inputText) {
                        Toast.makeText(fragmentActivity, "输入内容：" + inputText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBtnClickCancel(DialogInterface dialogInterface) {
                        Toast.makeText(fragmentActivity, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        findViewById(R.id.id_btn_classic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showClassicDialog(fragmentActivity, "温馨提示", "提示内容", new OnBtnClickListener() {
                    @Override
                    public void onBtnClickOk(DialogInterface dialogInterface) {
                        Toast.makeText(fragmentActivity, "ok", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onBtnClickCancel(DialogInterface dialogInterface) {
                        Toast.makeText(fragmentActivity, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
      /*  TextView textView = new TextView(this);
        textView.setText("textView");
        TextView textView_content = new TextView(this);
        textView_content.setText("textView_content");
        ClassicDialogFragment classicDialogFragment =new  ClassicDialogFragment.Builder(this)
                .setContentView(textView_content).build();
        classicDialogFragment .show(getSupportFragmentManager(),"DASDAS");*/
        /* DialogManager.showTipDialog(this,"123","456");
         */
//        TextView textView = new TextView(this);
//        textView.setText("textView");
//        DialogManager.showTipDialog(this, textView, "456");

   /*     DialogManager.showTipDialog(this, "温馨提示", "showTipDialog", new ClassicDialogFragment.OnBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface) {
                super.onBtnClickOk(dialogInterface);
                Toast.makeText(MainActivity.this, "onBtnClickOk", Toast.LENGTH_SHORT).show();
            }
        });


        DialogManager.showClassicDialog(this, "温馨提示", "showClassicDialog", new ClassicDialogFragment.OnBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface) {
                super.onBtnClickOk(dialogInterface);
                Toast.makeText(MainActivity.this, "onBtnClickOk", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBtnClickCancel(DialogInterface dialogInterface) {
                super.onBtnClickCancel(dialogInterface);
                Toast.makeText(MainActivity.this, "onBtnClickCancel", Toast.LENGTH_SHORT).show();
            }
        });*/
     /*   DialogManager.showCustomLoadingDialog(this);
        countDownTimer.start();*/

        //  DialogManager.showLoadingDialog(this);
        //  DialogManager.showTipDialog();
        // DialogManager.showCustomLoadingDialog(this);
        //## DialogManager.hideLoadingDialog();
     /*   DialogManager.showEditDialog(this, "温馨提示", "文本输入", new DialogManager.OnEditBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface, String inputText) {
                super.onBtnClickOk(dialogInterface, inputText);
                Toast.makeText(MainActivity.this, "onBtnClickOk inputText:" + inputText, Toast.LENGTH_SHORT).show();
            }
        });*/


        ClassicDialogFragment classicDialogFragment =
                new ClassicDialogFragment.Builder(this)
                        .setTitle("test")
                        .setMessage("message")
                        .setBackgroundColorValue(String.valueOf(Color.WHITE))
                        .setOkText("确认")
                        .setCancelText("取消")
                        .setOnBtnClickListener(new OnBtnClickListener() {
                            @Override
                            public void onBtnClickOk(DialogInterface dialogInterface) {
                                super.onBtnClickOk(dialogInterface);
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
        classicDialogFragment.show(getSupportFragmentManager(), "DASDAS");


    }
}
