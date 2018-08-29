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
                            Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_LONG).show();
                        }
                    },null);
                }
                fingerprintIdentifyView.showRxDialogFingerPrint();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
