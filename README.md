# FingerprintIdentifyView
这是一个用于指纹支付或者指纹登录的控件
###使用
* 1、导入 compile 'me.lyc.FingerprintIdentify:FingerprintIdentifyLib:1.0.0'
* 2、使用
```java 
                if (fingerprintIdentifyView == null){
                    fingerprintIdentifyView = new FingerprintIdentifyView(MainActivity.this, new FingerprintIdentifyView.FingerprintIdentifyViewReturn() {
                        @Override
                        public void onSucceed() {
                            //指纹识别成功回调
                            Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailed() {
                            //指纹识别失败回调
                            Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
                        }
                    },null);//mContentMsg是提示框上面的文字默认文字（请验证指纹用于登陆）
                }
                //先判断手机是否支持指纹识别
                // 指纹硬件可用并已经录入指纹
                // 指纹硬件是否可用
                // 是否已经录入指纹
                //只有满足三者才显示弹出框验证指纹
                if (fingerprintIdentifyView.isSupportFingerprint()){
                    fingerprintIdentifyView.showRxDialogFingerPrint();
                }
```
* 3、记得在onPause()和onDestroy()销毁
```java 
    protected void onPause() {
        super.onPause();
        if (fingerprintIdentifyView!=null){
            //销毁指纹识别
            fingerprintIdentifyView.fingerprintIdentifyClose();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        if (fingerprintIdentifyView!=null){
            //销毁指纹识别
            fingerprintIdentifyView.fingerprintIdentifyClose();
        }
    }
```
###此库集成了com.wei.android.lib:fingerprintidentify


