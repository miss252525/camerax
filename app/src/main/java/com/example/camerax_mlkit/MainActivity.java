package com.example.camerax_mlkit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import com.google.mlkit.vision.demo.java.CameraXLivePreviewActivity;
//import com.google.mlkit.vision.demo.java.ChooserActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //カメラパーミッション確認
        int cameraPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            //許可されていない場合は、リクエストダイアログ表示
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 1001);
        }

            // ボタン押下でカメラアプリを起動
            findViewById(R.id.button).setOnClickListener(
                    view -> {
                            Intent intent = new Intent(MainActivity.this, CameraXLivePreviewActivity.class);
                            startActivity(intent);

                    }
            );
        }

}