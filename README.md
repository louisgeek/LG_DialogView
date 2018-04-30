# ClassicDialogView
ClassicDialogView

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency  [![](https://jitpack.io/v/louisgeek/ClassicDialogView.svg)](https://jitpack.io/#louisgeek/ClassicDialogView)

	dependencies {
	        compile 'com.github.louisgeek:ClassicDialogView:x.x.x'
	}




![1](https://github.com/louisgeek/ClassicDialogView/blob/master/screenshots/pic.png)

![2](https://github.com/louisgeek/ClassicDialogView/blob/master/screenshots/pic2.png)

![3](https://github.com/louisgeek/ClassicDialogView/blob/master/screenshots/pic3.png)

![4](https://github.com/louisgeek/ClassicDialogView/blob/master/screenshots/pic4.png)

![5](https://github.com/louisgeek/ClassicDialogView/blob/master/screenshots/pic5.png)

![6](https://github.com/louisgeek/ClassicDialogView/blob/master/screenshots/pic6.png)


提示框

   		DialogManager.showTipDialog(this, "温馨提示", "showTipDialog", new ClassicDialogFragment.OnBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface) {
                super.onBtnClickOk(dialogInterface);
                Toast.makeText(MainActivity.this, "onBtnClickOk", Toast.LENGTH_SHORT).show();
            }
        });


询问框


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
        });


加载框

        DialogManager.showLoadingDialog(this);

		DialogManager.hideLoadingDialog();

		DialogManager.hideLoadingDialogAutoAfterTip("加载完成");


自定义加载框

        DialogManager.showCustomLoadingDialog(this);

		DialogManager.hideLoadingDialog();
		
		DialogManager.hideLoadingDialogAutoAfterTip("加载完成");
		//show tip 2000毫秒后自动关闭
		DialogManager.hideLoadingDialogAutoAfterTip("加载完成",2000);
 		//show tip 1000毫秒后自动关闭
		DialogManager.hideLoadingDialogAutoAfterTip("加载完成",1000, new DialogManager.OnAutoHide() {
                @Override
                public void autoHide() {
                    //do some thing
                }
            });


输入框

       DialogManager.showEditDialog(this, "温馨提示", "文本输入", new DialogManager.OnEditDialogBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface, String inputText) {
                super.onBtnClickOk(dialogInterface, inputText);
                Toast.makeText(MainActivity.this, "onBtnClickOk inputText:" + inputText, Toast.LENGTH_SHORT).show();
            }
        });
	
自定义contentView框 

		 mCustomDialogFragment = new ClassicDialogFragment.Builder(fragmentActivity)
                .setTitle(title)
                .setMessage(message)
                .setContentView(contentView)
				.setOkText(okText)
                .setCancelText(cancelText)
                .build();
		...
	
