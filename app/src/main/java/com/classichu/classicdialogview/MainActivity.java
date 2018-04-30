package com.classichu.classicdialogview;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.classichu.dialogview.manager.DialogManager;

public class MainActivity extends AppCompatActivity {

    private  CountDownTimer countDownTimer=new CountDownTimer(2*1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

            DialogManager.hideLoadingDialogAutoAfterTip("加载完成", 1000,new DialogManager.OnAutoHide() {
                @Override
                public void autoHide() {
                    //do some thing
                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  TextView textView = new TextView(this);
        textView.setText("textView");
        TextView textView_content = new TextView(this);
        textView_content.setText("textView_content");
        ClassicDialogFragment classicDialogFragment =new  ClassicDialogFragment.Builder(this)
                .setContentView(textView_content).build();
        classicDialogFragment .show(getSupportFragmentManager(),"DASDAS");*/
       /* DialogManager.showTipDialog(this,"123","456");
*/
        TextView textView = new TextView(this);
        textView.setText("textView");
        DialogManager.showTipDialog(this,textView,"456");

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
     /*   DialogManager.showEditDialog(this, "温馨提示", "文本输入", new DialogManager.OnEditDialogBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface, String inputText) {
                super.onBtnClickOk(dialogInterface, inputText);
                Toast.makeText(MainActivity.this, "onBtnClickOk inputText:" + inputText, Toast.LENGTH_SHORT).show();
            }
        });*/


    }
}
