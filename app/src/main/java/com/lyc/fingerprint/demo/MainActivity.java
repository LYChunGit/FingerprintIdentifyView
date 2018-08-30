package com.lyc.fingerprint.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wei.android.lib.fingerprintidentify.view.FingerprintIdentifyView;

public class MainActivity extends AppCompatActivity {
    Button start;
    FingerprintIdentifyView fingerprintIdentifyView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fingerprintIdentifyView!=null){
            //销毁指纹识别
            fingerprintIdentifyView.fingerprintIdentifyClose();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fingerprintIdentifyView!=null){
            //销毁指纹识别
            fingerprintIdentifyView.fingerprintIdentifyClose();
        }
    }
}
